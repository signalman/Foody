package com.foody.food.service;

import com.foody.food.dto.response.FoodSearchResponse;
import com.foody.food.entity.FoodSearch;
import com.foody.food.exception.FoodException;
import com.foody.food.repository.FoodSearchRepository;
import com.foody.global.exception.ErrorCode;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.connection.RedisZSetCommands.Limit;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FoodService {

    private final FoodSearchRepository foodSearchRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String SEARCH_KEY = "search:keywords";
    private static final String KEY_UPDATE = "update:keywords";
    private static final String SCORE_KEYWORD = "score:keywords";

    public FoodSearch findFoodSearchByName(String name){
        return foodSearchRepository.findFirstByName(name)
                                       .orElseThrow(() -> new FoodException(ErrorCode.FOOD_NOT_FOUND));
    }
    public FoodSearch findByName(String name){
        return foodSearchRepository.findByName(name)
                                   .orElseThrow(() -> new FoodException(ErrorCode.FOOD_NOT_FOUND));
    }


    /**
    * v1, v2 - Mysql full table scan
    * */
    public Set<String> getFoodSuggestionsV1andV2(String prefix, int limit){
        return foodSearchRepository.findByNameStartingWith(prefix)
                                                  .stream()
                                                  .limit(limit)
                                                  .map(food -> food.getName())
                                                  .collect(Collectors.toSet());
    }

    /**
    * v3 - Mysql index 설정 후 limit 쿼리 활용
    * */
    public Set<String> getFoodSuggestionsV3(String prefix, int limit){
        return foodSearchRepository.findByNameStartingWith(prefix, PageRequest.of(0, limit))
                                   .stream()
                                   .map(food -> food.getName())
                                   .collect(
                                       Collectors.toSet());
    }

    /**
    * v4 - Redis에서 조회
    * */
    public Set<String> getFoodSuggestionsV4(String prefix, int limit) {
        String endRange = prefix + "\uffff";
        log.info("{}", prefix, endRange);
        return redisTemplate.opsForZSet().rangeByLex(SEARCH_KEY, Range.range().gte(prefix).lt(endRange), Limit.limit().count(limit));
    }

    /**
     * write back 전략으로 캐시와 DB에 증가값 저장
     * */
    public FoodSearchResponse getFoodByName(String name) {
        redisTemplate.opsForZSet().incrementScore(SCORE_KEYWORD, name, 1);
        redisTemplate.opsForZSet().incrementScore(KEY_UPDATE, name, 1); //write back 방식으로 db에 추후 반영
        return new FoodSearchResponse(findFoodSearchByName(name));
    }

    @Scheduled(fixedDelay = 3000000L) //50분
    public void redisToDB(){
        //업데이트 할 tuple들 가져오기
        Set<TypedTuple<String>> foodsToUpdate = redisTemplate.opsForZSet()
                                                           .rangeWithScores(KEY_UPDATE, 0, -1);
        redisTemplate.delete(KEY_UPDATE);
        try {
            foodsToUpdate.forEach(
                food -> findByName(food.getValue()).increaseScore(food.getScore()));
        } catch (Exception e) {
            //예외 발생시 rollback 처리 위함
            redisTemplate.opsForZSet().add(KEY_UPDATE, foodsToUpdate);
        }
    }

    public Set<String> getHotKeywords() {
        return redisTemplate.opsForZSet().reverseRange(SCORE_KEYWORD, 0, 9);
    }
}
