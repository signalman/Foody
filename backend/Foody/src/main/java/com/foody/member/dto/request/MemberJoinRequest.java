package com.foody.member.dto.request;

public record MemberJoinRequest(
    String nickname,
    double height,
    double weight,
    int gender,
    int age,
    int activityLevel
) {

}
