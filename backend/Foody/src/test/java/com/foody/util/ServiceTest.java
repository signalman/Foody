package com.foody.util;

import com.foody.mbti.entity.Mbti;
import com.foody.mbti.repository.MbtiRepository;
import com.foody.member.entity.Member;
import com.foody.member.service.MemberService;
import com.foody.refrigerators.service.RefrigeratorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
public class ServiceTest {

    @Autowired
    protected MemberService memberService;

    @Autowired
    protected RefrigeratorsService refrigeratorsService;

    @Autowired
    protected MbtiRepository mbtiRepository;
    @Transactional
    protected void memberInfoGenerator() {

        Mbti mbti = new Mbti(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        mbtiRepository.save(mbti);
        Member member = Member.builder()
            .email("lkm454545@gmail.com")
            .nickname("세바스찬")
            .height(187.46)
            .weight(78.87f)
            .age(25)
            .activityLevel(1)
            .profileImg("PROFILE_IMAGE")
            .mbti(mbti)
            .build();

        memberService.save(member);
    }
}
