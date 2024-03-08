package com.foody.detect.service;

import com.foody.detect.dto.DetectResponse;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class DetectService {

    private final WebClient.Builder webClientBuilder;
    private WebClient webClient;

    @PostConstruct
    private void init(){
        this.webClient = webClientBuilder.baseUrl("http://localhost:5000").build();
    }


    /*
     * 2. client -> backend -> yolo 서버 (동기 요청, yolo 서버(Flask) 內 함수 동기)
     * */
    public List<DetectResponse> detectImageV2(MultipartFile file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", file.getResource());
        List<DetectResponse> responseList = webClient.post()
                                                     .uri("/detect")
                                                     .contentType(MediaType.MULTIPART_FORM_DATA)
                                                     .body(BodyInserters.fromMultipartData(builder.build()))
                                                     .retrieve() //응답을 처리하기 위한 체인 시작
                                                     .bodyToMono(new ParameterizedTypeReference<List<DetectResponse>>() {})
                                                     .block(); // 동기적으로 결과를 기다림
        return responseList;
    }


    /*
     * 3. client -> backend -> yolo 서버 (비동기 요청, yolo 서버(Flask) 內 함수 동기)
     * */
    public Mono<List<DetectResponse>> detectImageV3(MultipartFile file) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", file.getResource());

        Mono<List<DetectResponse>> responseList = webClient.post()
                                                   .uri("/detect")
                                                   .contentType(MediaType.MULTIPART_FORM_DATA)
                                                   .body(BodyInserters.fromMultipartData(
                                                       builder.build()))
                                                   .retrieve() //응답을 처리하기 위한 체인 시작
                                                   .bodyToMono( //응답 body를 비동기적으로 처리
                                                       new ParameterizedTypeReference<>() {});
        return responseList;
    }


    /*
     * 4. client -> backend -> yolo 서버 (비동기 요청, yolo 서버(FastAPI) 內 함수 비동기)
     * */
    public Mono<List<DetectResponse>> detectImageV4(MultipartFile file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("image", file.getResource());

        Mono<List<DetectResponse>> responseList = webClient.post()
                                                           .uri("/detect")
                                                           .contentType(
                                                               MediaType.MULTIPART_FORM_DATA)
                                                           .body(BodyInserters.fromMultipartData(
                                                               builder.build()))
                                                           .retrieve()
                                                           .bodyToMono(
                                                               new ParameterizedTypeReference<>() {
                                                               });
        return responseList;
    }
}
