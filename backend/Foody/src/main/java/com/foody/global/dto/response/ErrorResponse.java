package com.foody.global.dto.response;

import com.foody.global.exception.ErrorCode;

public record ErrorResponse(
        String code,
        ErrorCode errorCode,
        String message
) {
}
