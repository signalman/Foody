package com.foody.mealplan.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class MealPlanException extends BaseException {

    public MealPlanException(ErrorCode errorCode) {
        super(errorCode);
    }
}
