package com.foody.recommend.dto.resquest;

import com.foody.mbti.dto.response.MbtiResponse;

public record CombineInfoForHybrid(
    MbtiResponse user_preference,
    MemberInfoInput user_data

) {

}
