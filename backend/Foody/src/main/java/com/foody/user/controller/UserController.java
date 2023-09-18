package com.foody.user.controller;

import com.foody.user.dto.response.TokenResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    // 추가정보 입력 받아서 회원가입

    @GetMapping("/test")
    public ResponseEntity<TokenResponse> test() {
        System.out.println("메롱");
        return ResponseEntity.ok().body(new TokenResponse(1L,"123","123"));
    }
}
