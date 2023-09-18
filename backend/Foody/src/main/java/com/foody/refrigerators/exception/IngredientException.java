package com.foody.refrigerators.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class IngredientException extends BaseException {
    public IngredientException(ErrorCode errorCode) {
        super(errorCode);
    }
}
