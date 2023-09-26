package com.foody.recommend.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class RecommendException extends BaseException {

    public RecommendException(ErrorCode errorCode) {
        super(errorCode);
    }
}
