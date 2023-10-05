package com.foody.mbti.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class MbtiException extends BaseException {

    public MbtiException(ErrorCode errorCode) { super(errorCode); }
}
