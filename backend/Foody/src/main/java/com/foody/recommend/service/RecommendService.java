package com.foody.recommend.service;

import com.foody.global.exception.ErrorCode;
import com.foody.mbti.dto.response.MbtiResponse;
import com.foody.mbti.entity.Mbti;
import com.foody.member.entity.Member;
import com.foody.member.service.MemberService;
import com.foody.nutrient.dto.response.NutrientResponse;
import com.foody.nutrient.service.NutrientService;
import com.foody.recipe.dto.response.RecipeListResponse;
import com.foody.recipe.service.RecipeService;
import com.foody.recommend.dto.resquest.CombineInfoForHybrid;
import com.foody.recommend.dto.resquest.CombineInfoForPreference;
import com.foody.recommend.dto.resquest.CombineInfoForRefrigerator;
import com.foody.recommend.dto.resquest.MemberInfoInput;
import com.foody.recommend.exception.RecommendException;
import com.foody.refrigerators.dto.response.UserRefrigeratorResponse;
import com.foody.refrigerators.service.RefrigeratorsService;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecommendService {

    private final RefrigeratorsService refrigeratorsService;
    private final RecipeService recipeService;
    private final MemberService memberService;
    private final NutrientService nutrientService;
    @Value("${recommend.server.url}")
    private String serverUrl;

    @Transactional(readOnly = true)
    public List<RecipeListResponse> findRecommendItemByIngredients(String email) {

        List<UserRefrigeratorResponse> refrigerator = refrigeratorsService.getUserRefrigerator(email);

        if (refrigerator.isEmpty()) {
            return new ArrayList<RecipeListResponse>();
        }

        String ingredients = getIngredientsString(refrigerator);
        NutrientResponse nutrient = new NutrientResponse(nutrientService.getNutrientForRecommendation(LocalDateTime.now(), email));
        CombineInfoForRefrigerator combineInfoForRefrigerator = new CombineInfoForRefrigerator(ingredients, nutrient);
        List<Long> recipeIds = ingredientSendToServer(combineInfoForRefrigerator);

        return recipeService.findRecipeListByRecommend(recipeIds);
    }

    private String getIngredientsString(List<UserRefrigeratorResponse> refrigerator) {

        return refrigerator.stream()
                           .map(UserRefrigeratorResponse::ingredientName)
                           .collect(Collectors.joining(" "));
    }

    // TODO : 테스트를 위해 PUBLIC으로 열어둠, 나중에 Private으로 닫아야 함
    public List<Long> ingredientSendToServer(CombineInfoForRefrigerator combineInfoForRefrigerator) {

        WebClient webClient = WebClient.builder()
                                       .build();

        URI uri = URI.create(serverUrl + "/recipes/nutrients/recommend");
        log.debug("starting inter-server communication for ingredient");
        List<Long> recommendItemList = webClient.post()
                                                         .uri(uri)
                                                         .contentType(MediaType.APPLICATION_JSON)
                                                         .body(BodyInserters.fromValue(combineInfoForRefrigerator))
                                                         .retrieve()
                                                         .onStatus(HttpStatus::is5xxServerError,
                                                             response -> Mono.error(
                                                                 new RecommendException(
                                                                     ErrorCode.BIGDATA_SERVER_ERROR)
                                                             ))
                                                         .bodyToMono(
                                                             new ParameterizedTypeReference<List<Long>>() {
                                                             })
                                                         .block();

        log.debug("inter-server communication terminated");

        return recommendItemList;
    }

    @Transactional(readOnly = true)
    public List<RecipeListResponse> findHybridItemByPreference(String email) {

        Member member = memberService.findByEmail(email);
        Mbti mbti = member.getMbti();

        MbtiResponse mbtiResponse = new MbtiResponse(mbti);
        MemberInfoInput memberInfoInput = new MemberInfoInput(member);
        CombineInfoForHybrid combineInfoForHybrid = new CombineInfoForHybrid(mbtiResponse, memberInfoInput);

        List<Long> hybridRecommendItemIds = preferenceSendToServer(combineInfoForHybrid);

        return recipeService.findRecipeListByRecommend(hybridRecommendItemIds);
    }

    public List<Long> preferenceSendToServer(CombineInfoForHybrid mbti) {

        WebClient webClient = WebClient.builder()
                                       .build();

        URI uri = URI.create(serverUrl + "/preference/hybrid");
        log.debug("starting inter-server communication for hybrid");
        List<Long> recommendItemList = webClient.post()
                                                .uri(uri)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .body(BodyInserters.fromValue(mbti))
                                                .retrieve()
                                                .onStatus(HttpStatus::is5xxServerError,
                                                    response -> Mono.error(
                                                        new RecommendException(
                                                            ErrorCode.BIGDATA_SERVER_ERROR)
                                                    ))
                                                .bodyToMono(
                                                    new ParameterizedTypeReference<List<Long>>() {
                                                    })
                                                .block();

        log.debug("inter-server communication terminated");

        return recommendItemList;
    }


    @Transactional(readOnly = true)
    public List<RecipeListResponse> findRecommendItemByPreferenceWithNutrient(String email) {

        Member member = memberService.findByEmail(email);
        // 결핍 영양소
        NutrientResponse nutrient = new NutrientResponse(nutrientService.getNutrientForRecommendation(LocalDateTime.now(), email));
        Mbti mbti = member.getMbti();
        // 회원의 취향
        MbtiResponse mbtiResponse = new MbtiResponse(mbti);

        CombineInfoForPreference combineInfoForPreference = new CombineInfoForPreference(nutrient, mbtiResponse);

        List<Long> ids = preferenceAndNutrientSendToServer(combineInfoForPreference);

        return recipeService.findRecipeListByRecommend(ids);
    }

    public List<Long> preferenceAndNutrientSendToServer(
        CombineInfoForPreference combineInfoForPreference) {

        WebClient webClient = WebClient.builder()
                                       .build();

        URI uri = URI.create(serverUrl + "/preference/nutrient");
        log.debug("starting inter-server communication for preference and nutrient");
        List<Long> recommendItemList = webClient.post()
                                                .uri(uri)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .body(BodyInserters.fromValue(
                                                    combineInfoForPreference))
                                                .retrieve()
                                                .onStatus(HttpStatus::is5xxServerError,
                                                    response -> Mono.error(
                                                        new RecommendException(
                                                            ErrorCode.BIGDATA_SERVER_ERROR)
                                                    ))
                                                .bodyToMono(
                                                    new ParameterizedTypeReference<List<Long>>() {
                                                    })
                                                .block();

        log.debug("inter-server communication terminated");

        return recommendItemList;
    }
}
