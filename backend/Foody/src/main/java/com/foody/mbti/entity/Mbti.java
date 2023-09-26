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

    // 종류별
    private int koreanMainDish; // 한식 메인디쉬
    private int westernMainDish; // 양식 메인디쉬
    private int sideDish; // 밑반찬
    private int dessert; // 디저트

    // 상황별
    private int dailyFood; // 일상
    private int festivalFood; // 접대
    private int convenienceFood; // 간편식
    private int snackFood; // 술(안주)
    private int etcFood; // 기타

    // 재료별
    private int meat; // 고기류
    private int vegetableSeafood; // 채소/해물류
    private int processedFood; // 가공식품류
    private int healthFood; // 건강류
    private int grain; // 주식류

    // 방법별
    private int lowCook; // 저온조리
    private int highCook; // 고온조리
    private int waterCook; // 수분조리
    private int rawCook; // 날것
    private int etcCook; // 기타
}
