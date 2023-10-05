package com.foody.mbti.controller;

import com.foody.mbti.dto.request.MbtiRequest;
import com.foody.mbti.dto.response.MbtiImgResponse;
import com.foody.mbti.service.MbtiService;
import com.foody.security.util.LoginInfo;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/mbti")
public class MbtiController {

    private final MbtiService mbtiService;

    @GetMapping("/")
    public ResponseEntity<MbtiImgResponse> initializeMbti(@AuthenticationPrincipal LoginInfo loginInfo) {
        log.info("{} initializeMbti", loginInfo.email());
        MbtiImgResponse mbtiImgResponse = new MbtiImgResponse(mbtiService.initailizeMbtiImage());

        return ResponseEntity.ok().body(mbtiImgResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createMbti(@AuthenticationPrincipal LoginInfo loginInfo, @RequestBody MbtiRequest mbtiRequest) {
        log.info("{} request mbti",loginInfo.email());

        mbtiService.createMbti(loginInfo.email(), mbtiRequest);
        return ResponseEntity.noContent().build();
    }
}
