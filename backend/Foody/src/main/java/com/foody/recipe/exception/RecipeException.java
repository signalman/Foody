package com.foody.recipe.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class RecipeException extends BaseException {

    public RecipeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
