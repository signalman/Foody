package com.foody.member.entity;

import com.foody.global.entity.UserInfo;
import com.foody.mbti.entity.Mbti;
import com.foody.mealplan.entity.MealPlan;
import com.foody.member.dto.request.MemberInfoModifyRequest;
import com.foody.member.dto.request.MemberJoinRequest;
import com.foody.member.dto.request.MemberSignupRequest;
import com.foody.nutrient.entity.Nutrient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

    private double height;

    private double weight;

    private int gender;

    private int age;

    private int activityLevel;

    private String profileImg;

    @OneToOne
    @JoinColumn(name = "nutrient_id")
    private Nutrient nutrient;

    @OneToOne
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MealPlan> mealPlans = new ArrayList<>();

    public void createNutrient(Nutrient nutrient) {
        this.nutrient = nutrient;
    }

    public void createMbti(Mbti mbti) {
        this.mbti = mbti;
    }

    public static Member signupMember(MemberSignupRequest memberSignupRequest) {
        return Member.builder()
                     .email(memberSignupRequest.email())
                     .profileImg(memberSignupRequest.profileImg())
                     .build();
    }

    public void joinMember(MemberJoinRequest memberJoinRequest) {
        this.nickname = memberJoinRequest.nickname();
        this.height = memberJoinRequest.height();
        this.weight = memberJoinRequest.weight();
        this.gender = memberJoinRequest.gender();
        this.age = memberJoinRequest.age();
        this.activityLevel = memberJoinRequest.activityLevel();
    }

    public void modifyMember(MemberInfoModifyRequest memberInfoModifyRequest) {
        this.nickname = memberInfoModifyRequest.nickname();
        this.height = memberInfoModifyRequest.height();
        this.weight = memberInfoModifyRequest.weight();
        this.gender = memberInfoModifyRequest.gender();
        this.age = memberInfoModifyRequest.age();
        this.activityLevel = memberInfoModifyRequest.activityLevel();
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