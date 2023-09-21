package com.foody.member.dto.response;

public record TokenResponse(
    Long id,
    String accessToken,
    String refreshToken
) {

}
