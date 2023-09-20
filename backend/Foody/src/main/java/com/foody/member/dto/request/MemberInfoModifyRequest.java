package com.foody.member.dto.request;

public record MemberInfoModifyRequest(
    String nickname,
    float height,
    float weight,
    int gender,
    int age,
    int activityLevel
) {

}
