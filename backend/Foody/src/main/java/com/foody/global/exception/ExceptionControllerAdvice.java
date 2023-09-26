package com.foody.global.exception;

import com.foody.global.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> ExceptionHandler(BaseException e) {
        log.error("Exception: {}", e.getMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new ErrorResponse(e.getErrorCode().getCode(), e.getErrorCode(), e.getErrorCode().getMessage()));
    }
}

