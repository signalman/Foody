package com.foody.recommend.dto.resquest;

import com.foody.member.entity.Member;

public record MemberInfoInput(
    double weight,
    int gender,
    int age,
    int activityLevel,
    double height
) {
    public MemberInfoInput(Member member) {
        this(
            member.getWeight(),
            member.getGender(),
            member.getAge(),
            member.getActivityLevel(),
            member.getHeight()
        );
    }

}
