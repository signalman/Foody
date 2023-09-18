package com.foody.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* RefrigeratorException */
    INGREDIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 재료입니다.");


    private final HttpStatus httpStatus;
    private final String Message;

}
