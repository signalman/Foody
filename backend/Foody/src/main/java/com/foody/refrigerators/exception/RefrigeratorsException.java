package com.foody.refrigerators.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class RefrigeratorsException extends BaseException {
    public RefrigeratorsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
