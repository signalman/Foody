package com.foody.member.entity;

import com.foody.global.entity.UserInfo;
import com.foody.member.dto.request.MemberJoinRequest;
import com.foody.member.dto.request.MemberSignupRequest;
import java.util.Collection;
import java.util.Map;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Entity
public class Member extends UserInfo {

    private String nickname;

    private float height;

    private float weight;

    private int gender;

    private int age;

    private int activityLevel;

    private String profileImg;

    public static Member signupMember(MemberSignupRequest memberSignupRequest) {
        return Member.builder()
                     .email(memberSignupRequest.email())
                     .profileImg(memberSignupRequest.profileImg())
                     .build();
    }

    public static Member fromJoinRequest(MemberJoinRequest memberJoinRequest) {
        return Member.builder()
                     .nickname(memberJoinRequest.nickname())
                     .height(memberJoinRequest.height())
                     .weight(memberJoinRequest.weight())
                     .gender(memberJoinRequest.gender())
                     .age(memberJoinRequest.age())
                     .activityLevel(memberJoinRequest.activityLevel())
                     .build();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}