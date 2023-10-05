package com.foody.member.dto.request;

public record MemberInfoModifyRequest(
    String nickname,
    double height,
    double weight,
    int gender,
    int age,
    int activityLevel
) {

}
