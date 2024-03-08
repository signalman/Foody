package com.foody.detect.controller;

import com.foody.detect.dto.DetectResponse;
import com.foody.detect.service.DetectService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/detect")
@Slf4j
public class DetectController {

    private final DetectService detectService;


    /*
    * 2. client -> backend -> yolo 서버 (동기 요청, yolo 서버(Flask) 內 함수 동기)
    * */
    @PostMapping(path ="/v2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<DetectResponse> detectImageV2(@RequestPart(name = "image") MultipartFile file){
        List<DetectResponse> detectResponseMono = detectService.detectImageV2(file);
        return detectResponseMono;
    }

    /*
    * 3. client -> backend -> yolo 서버 (비동기 요청, yolo 서버(Flask) 內 함수 동기)
    * */
    @PostMapping(path = "/v3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<List<DetectResponse>> detectImageV3(@RequestPart(name = "image") MultipartFile file){
        Mono<List<DetectResponse>> detectResponseMono = detectService.detectImageV3(file);
         return detectResponseMono;
    }

    /*
     * 4. client -> backend -> yolo 서버 (비동기 요청, yolo 서버(FastAPI) 內 함수 비동기)
     * */
    @PostMapping(path = "/v4", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<List<DetectResponse>> detectImageV4(@RequestPart(name = "image") MultipartFile file){
        Mono<List<DetectResponse>> detectResponseMono = detectService.detectImageV4(file);
        return detectResponseMono;
    }
    
    

}
