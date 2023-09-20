package com.foody.security.service;

import com.foody.global.exception.ErrorCode;
import com.foody.member.exception.MemberException;
import com.foody.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 해당 이메일의 사용자가 있는지 검사
        return memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(ErrorCode.EMAIL_NOT_FOUND));
    }
}
