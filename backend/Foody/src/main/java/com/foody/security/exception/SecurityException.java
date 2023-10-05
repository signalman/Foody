package com.foody.security.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class SecurityException extends BaseException {
    public SecurityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
