package com.foody.mbti.entity;

import com.foody.global.entity.BaseEntity;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Mbti extends BaseEntity {

    private int koreanMainDish;
    private int westernMainDish;
}
