package com.foody.food.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foody.food.dto.response.FoodSearchResponse;
import com.foody.food.entity.Food;
import com.foody.food.entity.FoodSearch;
import com.foody.food.exception.FoodException;
import com.foody.food.repository.FoodRepository;
import com.foody.food.repository.FoodSearchRepository;
import com.foody.global.exception.ErrorCode;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisZSetCommands.Range;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FoodService {

    private final FoodRepository foodRepository;
    private final FoodSearchRepository foodSearchRepository;
    private final RedisTemplate redisTemplate;
    private static final String SEARCH_KEY = "search:keywords";
    private final ObjectMapper objectMapper;


    public Food findByName(String name){
        return foodRepository.findByName(name)
                             .orElseThrow(() -> new FoodException(ErrorCode.FOOD_NOT_FOUND));
    }
    public FoodSearch findFoodSearchByName(String name){
        return foodSearchRepository.findFirstByName(name)
                                       .orElseThrow(() -> new FoodException(ErrorCode.FOOD_NOT_FOUND));
    }
    public Set<String> getFoodSuggestions(String prefix, int limit) {
        log.info("Prefix: {}", prefix);
        // 접두사의 다음 문자열을 찾기 위해 유니코드 값을 1 증가시킵니다.
        String nextPrefix = prefix.substring(0, prefix.length() - 1) + (char) (prefix.charAt(prefix.length() - 1) + 1);
        log.info("nextPrefix: {}", nextPrefix);
        // score(점수)에 따라 정렬된 항목 중에서 검색어에 해당하는 범위의 항목들을 조회합니다.
        Set<String> results = redisTemplate.opsForZSet().reverseRangeByLex(SEARCH_KEY, Range.range().gte(prefix).lt(nextPrefix));
        log.info("results: {}", results);

        // 결과를 제한합니다.
        if (results.size() > limit) {
            return results.stream().limit(limit).collect(Collectors.toSet());
        } else {
            return results;
        }
    }

    /*
    redis에서 네이티브 쿼리사용
    */

    /*
    * db에서 조회 - 1 모든 요소 탐색 후 스프링 단에서 처리
    * */
    public Set<String> getFoodSuggestionsV1(String prefix, int limit){
        return foodSearchRepository.findByNameContaining(prefix)
                                                  .stream()
                                                  .limit(limit)
                                                  .map(food -> food.getName())
                                                  .collect(Collectors.toSet());
    }
    /*
    * db에서 조회 - 2 쿼리 시 가져오기
    *
    * */


    /*
    * redis에서 가져온 후
    * */


    public void saveFoodsFromCSV() throws Exception {

        // CSV 파일에서 Food 객체 리스트 생성
        List<FoodSearch> foods = readFoodsFromCSV();

        for (FoodSearch food : foods) {
            String key = "FOOD:" + food.getName();
            String jsonValue = objectMapper.writeValueAsString(food);
            redisTemplate.opsForValue().set(key, jsonValue);
            redisTemplate.opsForZSet().add(SEARCH_KEY, food.getName(), 0);
        }
    }

    private List<FoodSearch> readFoodsFromCSV() throws Exception {
        List<FoodSearch> foods = new ArrayList<>();

        ClassPathResource resource = new ClassPathResource("food/foody_food.csv");
        try (Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            CSVReader csvReader = new CSVReader(reader)) {
            // 첫 번째 줄은 헤더이므로 생략
            String[] nextRecord;
            csvReader.readNext();

            while ((nextRecord = csvReader.readNext()) != null) {
                FoodSearch foodSearch = new FoodSearch();
                foodSearch.setId(parseLongSafe(nextRecord[0]));
                foodSearch.setName(nextRecord[1]);
                foodSearch.setEnergy(parseDoubleSafe(nextRecord[2]));
                foodSearch.setCarbohydrates(parseDoubleSafe(nextRecord[3]));
                foodSearch.setProtein(parseDoubleSafe(nextRecord[4]));
                foodSearch.setDietaryFiber(parseDoubleSafe(nextRecord[5]));
                foodSearch.setCalcium(parseDoubleSafe(nextRecord[6]));
                foodSearch.setSodium(parseDoubleSafe(nextRecord[7]));
                foodSearch.setIron(parseDoubleSafe(nextRecord[8]));
                foodSearch.setFats(parseDoubleSafe(nextRecord[9]));
                foodSearch.setVitaminA(parseDoubleSafe(nextRecord[10]));
                foodSearch.setVitaminC(parseDoubleSafe(nextRecord[11]));
                foods.add(foodSearch);
            }
        }
        return foods;
    }

    private double parseDoubleSafe(String value) {
        return value != null && !value.trim().isEmpty() ? Double.parseDouble(value) : 0.0;
    }

    private long parseLongSafe(String value) {
        return value != null && !value.trim().isEmpty() ? Long.parseLong(value) : 0;
    }

    public FoodSearchResponse getFoodNameByName(String name) {
        FoodSearch findFood = findFoodSearchByName(name);
        FoodSearchResponse foodResponse = new FoodSearchResponse(findFood);
        return foodResponse;
    }
}
