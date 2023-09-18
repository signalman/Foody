package com.foody.user.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class UserException extends BaseException {

    public UserException(ErrorCode errorCode) { super(errorCode); }
}
