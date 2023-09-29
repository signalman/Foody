package com.foody.food.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class FoodException extends BaseException {

    public FoodException(ErrorCode errorCode) {
        super(errorCode);
    }
}
