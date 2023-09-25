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

        arr[0][0] = "갈비찜";
        arr[0][1] = baseImgURL + "00.jfif";
        arr[1][0] = "쌀국수";
        arr[1][1] = baseImgURL + "01.jfif";
        arr[2][0] = "동그랑땡";
        arr[2][1] = baseImgURL + "02.jpg";
        arr[3][0] = "팥빙수";
        arr[3][1] = baseImgURL + "03.jfif";
        arr[4][0] = "김치";
        arr[4][1] = baseImgURL + "04.jfif";
        arr[5][0] = "짜장면";
        arr[5][1] = baseImgURL + "05.jfif";
        arr[6][0] = "시금치 나물";
        arr[6][1] = baseImgURL + "06.jfif";
        arr[7][0] = "마카롱";
        arr[7][1] = baseImgURL + "07.jfif";
        arr[8][0] = "육회";
        arr[8][1] = baseImgURL + "08.jfif";
        arr[9][0] = "샐러드";
        arr[9][1] = baseImgURL + "09.jfif";
        arr[10][0] = "숙주 나물";
        arr[10][1] = baseImgURL + "10.jfif";
        arr[11][0] = "두부김치";
        arr[11][1] = baseImgURL + "11.jfif";
        arr[12][0] = "순대";
        arr[12][1] = baseImgURL + "12.jfif";
        arr[13][0] = "오징어 젓갈";
        arr[13][1] = baseImgURL + "13.jfif";
        arr[14][0] = "마라탕";
        arr[14][1] = baseImgURL + "14.jfif";
        arr[15][0] = "탕후루";
        arr[15][1] = baseImgURL + "15.jfif";
        arr[16][0] = "과일화채";
        arr[16][1] = baseImgURL + "16.jfif";
        arr[17][0] = "메밀소바";
        arr[17][1] = baseImgURL + "17.jfif";
        arr[18][0] = "고등어조림";
        arr[18][1] = baseImgURL + "18.jpg";
        arr[19][0] = "장조림";
        arr[19][1] = baseImgURL + "19.jpg";
        arr[20][0] = "크로플";
        arr[20][1] = baseImgURL + "20.jpg";
        arr[21][0] = "새우튀김";
        arr[21][1] = baseImgURL + "21.jpg";
        arr[22][0] = "리면";
        arr[22][1] = baseImgURL + "22.jpg";
        arr[23][0] = "타토야끼";
        arr[23][1] = baseImgURL + "23.jpg";
        arr[24][0] = "된장국";
        arr[24][1] = baseImgURL + "24.jpg";
        arr[25][0] = "카레";
        arr[25][1] = baseImgURL + "25.jpg";
        arr[26][0] = "탕수육";
        arr[26][1] = baseImgURL + "26.jpg";
        arr[27][0] = "파전";
        arr[27][1] = baseImgURL + "27.jfif";

        return arr;
    }
}
