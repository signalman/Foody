package com.foody.nutrient.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class NutrientException extends BaseException {
    public NutrientException(ErrorCode errorCode) { super(errorCode); }
}
