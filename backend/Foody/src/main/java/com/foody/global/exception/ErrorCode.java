package com.foody.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // UserException
    NICKNAME_DUPLICATED("USER01", HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다."),
    EMAIL_DUPLICATED("USER01",HttpStatus.CONFLICT,"이미 가입된 이메일입니다."),
    EMAIL_NOT_FOUND("USEE03",HttpStatus.NOT_FOUND,"이메일이 존재하지 않습니다."),

     /* RefrigeratorException */
    INGREDIENT_NOT_FOUND("REFRI07", HttpStatus.NOT_FOUND, "존재하지 않는 재료입니다.");



    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
