package com.foody.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // MemberException
    NICKNAME_DUPLICATED("MEM01", HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다."),
    EMAIL_DUPLICATED("MEM02",HttpStatus.CONFLICT,"이미 가입된 이메일입니다."),
    EMAIL_NOT_FOUND("MEM03",HttpStatus.NOT_FOUND,"이메일이 존재하지 않습니다."),
    INVALID_REFRESH_TOKEN("MEM04", HttpStatus.BAD_REQUEST, "유효한 토큰이 아닙니다."),

     /* RefrigeratorException */
    INGREDIENT_NOT_FOUND("REFRI07", HttpStatus.NOT_FOUND, "존재하지 않는 재료입니다."),
    INGREDIENT_CATEGORY_NOT_FOUND("REFRI07", HttpStatus.NOT_FOUND, "존재하지 않는 재료 카테고리입니다."),
    USER_INGREDIENT_NOT_FOUND("REFRI08", HttpStatus.NOT_FOUND, "사용자의 냉장고에 존재하지 않는 재료입니다."),

    // RecommendedNutrient
    RECOMMENED_NOT_FOUND("RECOM01", HttpStatus.NOT_FOUND, "정보가 존재하지 않습니다.");



    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
