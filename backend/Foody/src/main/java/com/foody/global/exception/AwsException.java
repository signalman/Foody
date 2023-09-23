package com.foody.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AwsException extends RuntimeException{

    private ErrorCode errorCode;
}
