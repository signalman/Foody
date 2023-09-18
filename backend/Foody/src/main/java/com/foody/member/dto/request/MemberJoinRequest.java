package com.foody.member.dto.request;

public record MemberJoinRequest(
    String nickname,
    float height,
    float weight,
    int gender,
    int age,
    int activityLevel
) {

}
