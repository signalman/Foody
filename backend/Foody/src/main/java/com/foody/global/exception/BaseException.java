package com.foody.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class BaseException extends RuntimeException{

    private ErrorCode errorCode;
}
