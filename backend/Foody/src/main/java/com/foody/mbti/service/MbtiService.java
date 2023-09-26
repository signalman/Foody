package com.foody.mbti.service;

import com.foody.mbti.dto.request.MbtiRequest;
import com.foody.mbti.entity.Mbti;
import com.foody.mbti.repository.MbtiRepository;
import com.foody.member.entity.Member;
import com.foody.member.repository.MemberRepository;
import com.foody.member.service.MemberService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MbtiService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final MbtiRepository mbtiRepository;

    @Transactional
    public void createMbti(String email, MbtiRequest mbtiRequest) {

        List<Integer> arr = mbtiRequest.results();
        // 종류별
        int koreanMainDish = 0; // 한식 메인디쉬
        int westernMainDish = 0; // 양식 메인디쉬
        int sideDish = 0; // 밑반찬
        int dessert = 0; // 디저트

        // 상황별
        int dailyFood = 0; // 일상
        int festivalFood = 0; // 접대
        int convenienceFood = 0; // 간편식
        int snackFood = 0; // 술(안주)
        int etcFood = 0; // 기타

        // 재료별
        int meat = 0; // 고기류
        int vegetableSeafood = 0; // 채소/해물류
        int processedFood = 0; // 가공식품류
        int healthFood = 0; // 건강류
        int grain = 0; // 주식류

        // 방법별
        int lowCook = 0; // 저온조리
        int highCook = 0; // 고온조리
        int waterCook = 0; // 수분조리
        int rawCook = 0; // 날것
        int etcCook = 0; // 기타

        Member member = memberService.findByEmail(email);

        // 갈비찜
        if(arr.get(0)==1){
            koreanMainDish++;
            festivalFood++;
            meat++;
            highCook++;
        } else if (arr.get(0)==2) {
            koreanMainDish+=3;
            festivalFood+=3;
            meat+=3;
            highCook+=3;
        }
        // 쌀국수
        if(arr.get(1)==1){
            westernMainDish++;
            dailyFood++;
            grain++;
            highCook++;
        } else if (arr.get(1)==2) {
            westernMainDish+=3;
            dailyFood+=3;
            grain+=3;
            highCook+=3;
        }
        // 동그랑땡
        if(arr.get(2)==1){
            sideDish++;
            snackFood++;
            meat++;
            lowCook++;
        } else if (arr.get(2)==2) {
            sideDish+=3;
            snackFood+=3;
            meat+=3;
            lowCook+=3;
        }
        // 팥빙수
        if(arr.get(3)==1){
            dessert++;
            convenienceFood++;
            processedFood++;
            rawCook++;
        } else if (arr.get(3)==2) {
            dessert+=3;
            convenienceFood+=3;
            processedFood+=3;
            rawCook+=3;
        }
        // 김치
        if(arr.get(4)==1){
            sideDish++;
            dailyFood++;
            vegetableSeafood++;
            rawCook++;
        } else if (arr.get(4)==2) {
            sideDish+=3;
            dailyFood+=3;
            vegetableSeafood+=3;
            rawCook+=3;
        }
        // 짜장면
        if(arr.get(5)==1){
            westernMainDish++;
            dailyFood++;
            grain++;
            waterCook++;
        } else if (arr.get(5)==2) {
            westernMainDish+=3;
            dailyFood+=3;
            grain+=3;
            waterCook+=3;
        }
        // 시금치
        if(arr.get(6)==1){
            sideDish++;
            dailyFood++;
            healthFood++;
            rawCook++;
        } else if (arr.get(6)==2) {
            sideDish+=3;
            dailyFood+=3;
            healthFood+=3;
            rawCook+=3;
        }
        // 마카롱
        if(arr.get(7)==1){
            dessert++;
            convenienceFood++;
            processedFood++;
            etcCook++;
        } else if (arr.get(7)==2) {
            dessert+=3;
            convenienceFood+=3;
            processedFood+=3;
            etcCook+=3;
        }
        // 육회
        if(arr.get(8)==1){
            koreanMainDish++;
            snackFood++;
            meat++;
            rawCook++;
        } else if (arr.get(8)==2) {
            koreanMainDish+=3;
            snackFood+=3;
            meat+=3;
            rawCook+=3;
        }
        // 샐러드
        if(arr.get(9)==1){
            westernMainDish++;
            convenienceFood++;
            vegetableSeafood++;
            rawCook++;
        } else if (arr.get(9)==2) {
            westernMainDish+=3;
            convenienceFood+=3;
            vegetableSeafood+=3;
            rawCook+=3;
        }
        // 숙주나물
        if(arr.get(10)==1){
            sideDish++;
            dailyFood++;
            vegetableSeafood++;
            waterCook++;
        } else if (arr.get(10)==2) {
            sideDish+=3;
            dailyFood+=3;
            vegetableSeafood+=3;
            waterCook+=3;
        }
        // 두부김치
        if(arr.get(11)==1){
            sideDish++;
            snackFood++;
            healthFood++;
            waterCook++;
        } else if (arr.get(11)==2) {
            sideDish+=3;
            snackFood+=3;
            healthFood+=3;
            waterCook+=3;
        }
        // 순대
        if(arr.get(12)==1){
            sideDish++;
            snackFood++;
            meat++;
            highCook++;
        } else if (arr.get(12)==2) {
            sideDish+=3;
            snackFood+=3;
            meat+=3;
            highCook+=3;
        }
        // 오징어젓갈
        if(arr.get(13)==1){
            sideDish++;
            dailyFood++;
            processedFood++;
            rawCook++;
        } else if (arr.get(13)==2) {
            sideDish+=3;
            dailyFood+=3;
            processedFood+=3;
            rawCook+=3;
        }
        // 마라탕
        if(arr.get(14)==1){
            westernMainDish++;
            etcFood++;
            vegetableSeafood++;
            highCook++;
        } else if (arr.get(14)==2) {
            koreanMainDish+=3;
            etcFood+=3;
            vegetableSeafood+=3;
            highCook+=3;
        }
        // 탕후루
        if(arr.get(15)==1){
            dessert++;
            dailyFood++;
            processedFood++;
            etcCook++;
        } else if (arr.get(15)==2) {
            dessert+=3;
            dailyFood+=3;
            processedFood+=3;
            etcCook+=3;
        }
        // 과일화채
        if(arr.get(16)==1){
            dessert++;
            snackFood++;
            healthFood++;
            rawCook++;
        } else if (arr.get(16)==2) {
            dessert+=3;
            snackFood+=3;
            healthFood+=3;
            rawCook+=3;
        }
        // 메밀소바
        if(arr.get(17)==1){
            westernMainDish++;
            dailyFood++;
            grain++;
            waterCook++;
        } else if (arr.get(17)==2) {
            westernMainDish+=3;
            dailyFood+=3;
            grain+=3;
            waterCook+=3;
        }
        // 고등어조림
        if(arr.get(18)==1){
            koreanMainDish++;
            dailyFood++;
            vegetableSeafood++;
            highCook++;
        } else if (arr.get(18)==2) {
            koreanMainDish+=3;
            dailyFood+=3;
            vegetableSeafood+=3;
            highCook+=3;
        }
        // 장조림
        if(arr.get(19)==1){
            sideDish++;
            dailyFood++;
            meat++;
            waterCook++;
        } else if (arr.get(19)==2) {
            sideDish+=3;
            dailyFood+=3;
            meat+=3;
            waterCook+=3;
        }
        // 크로플
        if(arr.get(20)==1){
            dessert++;
            convenienceFood++;
            grain++;
            lowCook++;
        } else if (arr.get(20)==2) {
            dessert+=3;
            convenienceFood+=3;
            grain+=3;
            lowCook+=3;
        }
        // 새우튀김
        if(arr.get(21)==1){
            sideDish++;
            dailyFood++;
            vegetableSeafood++;
            highCook++;
        } else if (arr.get(21)==2) {
            sideDish+=3;
            dailyFood+=3;
            vegetableSeafood+=3;
            highCook+=3;
        }
        // 라면
        if(arr.get(22)==1){
            koreanMainDish++;
            convenienceFood++;
            processedFood++;
            highCook++;
        } else if (arr.get(22)==2) {
            koreanMainDish+=3;
            convenienceFood+=3;
            processedFood+=3;
            highCook+=3;
        }
        // 타코야끼
        if(arr.get(23)==1){
            dessert++;
            dailyFood++;
            vegetableSeafood++;
            lowCook++;
        } else if (arr.get(23)==2) {
            dessert+=3;
            dailyFood+=3;
            vegetableSeafood+=3;
            lowCook+=3;
        }
        // 된장국
        if(arr.get(24)==1){
            sideDish++;
            dailyFood++;
            vegetableSeafood++;
            highCook++;
        } else if (arr.get(24)==2) {
            sideDish+=3;
            dailyFood+=3;
            vegetableSeafood+=3;
            highCook+=3;
        }
        // 카레
        if(arr.get(25)==1){
            westernMainDish++;
            convenienceFood++;
            processedFood++;
            lowCook++;
        } else if (arr.get(25)==2) {
            westernMainDish+=3;
            convenienceFood+=3;
            processedFood+=3;
            lowCook+=3;
        }
        // 탕수육
        if(arr.get(26)==1){
            westernMainDish++;
            festivalFood++;
            meat++;
            highCook++;
        } else if (arr.get(26)==2) {
            westernMainDish+=3;
            festivalFood+=3;
            meat+=3;
            highCook+=3;
        }
        // 파전
        if(arr.get(27)==1){
            koreanMainDish++;
            etcFood++;
            vegetableSeafood++;
            lowCook++;
        } else if (arr.get(27)==2) {
            koreanMainDish+=3;
            etcFood+=3;
            vegetableSeafood+=3;
            lowCook+=3;
        }

        Mbti mbti = new Mbti(koreanMainDish, westernMainDish, sideDish, dessert, dailyFood, festivalFood, convenienceFood, snackFood, etcFood, meat, vegetableSeafood, processedFood, healthFood, grain, lowCook, highCook, waterCook, rawCook, etcCook);

        mbtiRepository.save(mbti);
        member.createMbti(mbti);
    }

    public String[][] initailizeMbtiImage() {
        String[][] arr = new String[28][2];
        String baseImgURL = "https://please-success-foody.s3.ap-northeast-2.amazonaws.com/mbti/mbti";

        String[] foods = {
            "갈비찜", "쌀국수", "동그랑땡", "팥빙수", "김치", "짜장면",
            "시금치 나물", "마카롱", "육회", "샐러드", "숙주 나물", "두부김치",
            "순대", "오징어 젓갈", "마라탕", "탕후루", "과일화채", "메밀소바",
            "고등어조림", "장조림", "크로플", "새우튀김", "라면", "타코야끼",
            "된장국", "카레", "탕수육", "파전"
        };

        String[] extensions = {
            "jfif", "jfif", "jpg", "jfif", "jfif", "jfif",
            "jfif", "jfif", "jfif", "jfif", "jfif", "jfif",
            "jfif", "jfif", "jfif", "jfif", "jfif", "jfif",
            "jpg", "jpg", "jpg", "jpg", "jpg", "jpg",
            "jpg", "jpg", "jpg", "jpg"
        };

        for (int i = 0; i < foods.length; i++) {
            arr[i][0] = foods[i];
            arr[i][1] = baseImgURL + String.format("%02d", i) + "." + extensions[i];
        }

        return arr;
    }
}
