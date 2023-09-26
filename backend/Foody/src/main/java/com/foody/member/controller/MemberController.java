package com.foody.member.controller;

import com.foody.global.service.AmazonS3Service;
import com.foody.member.dto.request.CheckNicknameRequest;
import com.foody.member.dto.request.MemberInfoModifyRequest;
import com.foody.member.dto.request.MemberJoinRequest;
import com.foody.member.dto.request.RefreshTokenRequest;
import com.foody.member.dto.response.RefreshTokenResponse;
import com.foody.member.dto.response.TokenResponse;
import com.foody.member.service.MemberService;
import com.foody.security.util.LoginInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final AmazonS3Service amazonS3Service;

    // 닉네임 중복 검사
    @GetMapping("/check")
    public ResponseEntity<String> checkNickname(@AuthenticationPrincipal LoginInfo loginInfo, @RequestBody CheckNicknameRequest checkNicknameRequest) {
        log.debug("'{}' check exists", checkNicknameRequest.nickname());
        memberService.isNicknameDuplicated(checkNicknameRequest.nickname());

        return ResponseEntity.noContent().build();
    }

    // 추가 정보 입력
    @PostMapping("/join")
    public ResponseEntity<Void> joinMember(@AuthenticationPrincipal LoginInfo loginInfo, @RequestBody MemberJoinRequest memberJoinRequest) {
        log.debug("{} member request join", loginInfo.email());

        memberService.joinMember(loginInfo.email(), memberJoinRequest);

        return ResponseEntity.noContent().build();
    }

    // 정보 수정
    @PutMapping("/info")
    public ResponseEntity<String> modifyMemeberInfo(@AuthenticationPrincipal LoginInfo loginInfo, @RequestBody
        MemberInfoModifyRequest memberInfoModifyRequest) {
        log.debug("'{}' member modify info", loginInfo.email());

        memberService.modifyMember(loginInfo.email(), memberInfoModifyRequest);

        return ResponseEntity.noContent().build();
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal LoginInfo loginInfo) {
        log.debug("'{}' logout", loginInfo.email());

        memberService.logout(loginInfo);
        return ResponseEntity.noContent().build();
    }

    // accessToekn 갱신
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@AuthenticationPrincipal LoginInfo loginInfo,@RequestBody final RefreshTokenRequest refreshTokenRequest) {
        log.debug("refresh accessToken");

        RefreshTokenResponse refreshTokenResponse = memberService.refreshToken(refreshTokenRequest);

        return ResponseEntity.ok().body(refreshTokenResponse);
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(@AuthenticationPrincipal LoginInfo loginInfo,
        @RequestPart(value = "profileImg", required = false) MultipartFile profileImg) {
         String name = amazonS3Service.uploadFile(profileImg);
        return ResponseEntity.ok().body(name);
    }
}
