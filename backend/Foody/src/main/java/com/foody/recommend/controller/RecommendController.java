package com.foody.recommend.controller;

import com.foody.recommend.service.RecommendService;
import com.foody.security.util.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommend")
@RestController
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/ingredients")
    public ResponseEntity<Void> recommendByIngredients(@AuthenticationPrincipal LoginInfo loginInfo) {


        return ResponseEntity.noContent().build();
    }

}
