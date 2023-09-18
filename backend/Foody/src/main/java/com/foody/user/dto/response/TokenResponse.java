package com.foody.user.dto.response;

public record TokenResponse(
    Long id,
    String accessToken,
    String refreshToken
) {

}
