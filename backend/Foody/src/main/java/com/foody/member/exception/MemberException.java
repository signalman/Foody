package com.foody.member.exception;

import com.foody.global.exception.BaseException;
import com.foody.global.exception.ErrorCode;

public class MemberException extends BaseException {

    public MemberException(ErrorCode errorCode) { super(errorCode); }
}
