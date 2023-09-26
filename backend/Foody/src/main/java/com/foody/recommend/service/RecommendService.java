package com.foody.recommend.service;

import com.foody.global.exception.ErrorCode;
import com.foody.recommend.dto.response.RecommendItem;
import com.foody.recommend.exception.RecommendException;
import com.foody.refrigerators.dto.response.UserRefrigeratorResponse;
import com.foody.refrigerators.service.RefrigeratorsService;
import java.net.URI;
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
    @Value("{recommend.server.url}")
    private String serverUrl;

    @Transactional(readOnly = true)
    public List<RecommendItem> findRecommendItemByIngredients(String email) {

        List<UserRefrigeratorResponse> refrigerator = refrigeratorsService.getUserRefrigerator(
            email);

        if (refrigerator.isEmpty()) {
            throw new RecommendException(ErrorCode.REFRIGERATOR_IS_EMPTY);
        }

        String ingredients = getIngredientsString(refrigerator);

        return ingredientSendToServer(ingredients);
    }

    private String getIngredientsString(List<UserRefrigeratorResponse> refrigerator) {

        return refrigerator.stream()
                           .map(UserRefrigeratorResponse::ingredientName)
                           .collect(Collectors.joining(" "));
    }

    private List<RecommendItem> ingredientSendToServer(String ingredients) {

        WebClient webClient = WebClient.builder()
                                       .build();

        URI uri = URI.create(serverUrl + "/recipe/nutrients/cosine");
        log.debug("starting inter-server communication for ingredient");
        List<RecommendItem> recommendItemList = webClient.post()
                                                         .uri(uri)
                                                         .contentType(MediaType.APPLICATION_JSON)
                                                         .body(BodyInserters.fromValue(ingredients))
                                                         .retrieve()
                                                         .onStatus(HttpStatus::is5xxServerError,
                                                             response -> Mono.error(
                                                                 new RecommendException(
                                                                     ErrorCode.BIGDATA_SERVER_ERROR)
                                                             ))
                                                         .bodyToMono(
                                                             new ParameterizedTypeReference<List<RecommendItem>>() {
                                                             })
                                                         .block();

        log.debug("inter-server communication terminated");

        return recommendItemList;
    }
}
