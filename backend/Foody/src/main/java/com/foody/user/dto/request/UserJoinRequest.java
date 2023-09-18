package com.foody.user.dto.request;

public record UserJoinRequest(
    String nickname,
    float height,
    float weight,
    int gender,
    int age,
    int activityLevel
) {

}
