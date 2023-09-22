package com.foody.recommendednutrient.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class RecommendedNutrientException extends BaseException {
    public RecommendedNutrientException(ErrorCode errorCode) { super(errorCode); }
}
