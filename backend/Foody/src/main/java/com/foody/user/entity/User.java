package com.foody.user.entity;

import com.foody.global.entity.UserInfo;
import com.foody.user.dto.request.UserJoinRequest;
import com.foody.user.dto.request.UserSignupRequest;
import java.util.Collection;
import java.util.Map;
import javax.persistence.Column;
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
public class User extends UserInfo {

    private String nickname;

    private float height;

    private float weight;

    private int gender;

    private int age;

    private int activityLevel;

    private String profileImg;

    public static User signupUser(UserSignupRequest userSignupRequest) {
        return User.builder()
            .email(userSignupRequest.email())
            .profileImg(userSignupRequest.profileImg())
            .build();
    }

    public static User fromJoinRequest(UserJoinRequest userJoinRequest) {
        return User.builder()
                   .nickname(userJoinRequest.nickname())
                   .height(userJoinRequest.height())
                   .weight(userJoinRequest.weight())
                   .gender(userJoinRequest.gender())
                   .age(userJoinRequest.age())
                   .activityLevel(userJoinRequest.activityLevel())
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