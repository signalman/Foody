package com.foody.nutrient.service;

import com.foody.global.exception.ErrorCode;
import com.foody.member.entity.Member;
import com.foody.member.exception.MemberException;
import com.foody.member.repository.MemberRepository;
import com.foody.nutrient.dto.response.NutrientResponse;
import com.foody.nutrient.entity.Nutrient;
import com.foody.nutrient.exception.NutrientException;
import com.foody.nutrient.repository.NutrientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NutrientService {

    private final MemberRepository memberRepository;
    private final NutrientRepository nutrientRepository;

    // 영양 정보 생성
    @Transactional
    public void createNutrient(String email) {
        Member member = memberRepository.findByEmail(email)
                                      .orElseThrow(() -> new MemberException(ErrorCode.EMAIL_NOT_FOUND));
        // 나이, 성별 바탕으로 생성 ->
        int age = member.getAge();
        int gender = member.getGender();

        double energy; // 칼로리
        double carbohydrates; // 탄수화물
        double protein; // 단백질
        double dietaryFiber; // 식이섬유
        double calcium; // 칼슘
        double sodium; // 나트륨
        double iron; // 철분
        double fats; // 지방
        double vitaminA; // 비타민A
        double vitaminC; // 비타민C


        carbohydrates = 130;
        if(gender==1) { // 남자
            if(age<=8){
                energy = 1700;
                protein = 35;
                dietaryFiber = 25;
                vitaminA = 450;
                vitaminC = 50;
                calcium = 700;
                sodium = 1200;
                iron = 9;
                fats = 10.1;
            }else if(age>=9 && age<=11){
                energy = 2000;
                protein = 50;
                dietaryFiber = 25;
                vitaminA = 600;
                vitaminC = 70;
                calcium = 800;
                sodium = 1500;
                iron = 11;
                fats = 10.8;
            }else if(age>=12 && age<=14){
                energy = 2500;
                protein = 60;
                dietaryFiber = 30;
                vitaminA = 750;
                vitaminC = 90;
                calcium =1000;
                sodium = 1500;
                iron = 14;
                fats = 13.5;
            }else if(age>=15 && age<=18){
                energy = 2700;
                protein = 65;
                dietaryFiber = 30;
                vitaminA = 850;
                vitaminC = 100;
                calcium = 900;
                sodium = 1500;
                iron = 14;
                fats = 15.7;
            }else if(age>=19 && age<=29){
                energy = 2600;
                protein = 65;
                dietaryFiber = 30;
                vitaminA = 800;
                vitaminC = 100;
                calcium = 800;
                sodium = 1500;
                iron = 10;
                fats = 14.6;
            }else if(age>=30 && age<=49){
                energy = 2500;
                protein = 65;
                dietaryFiber = 30;
                vitaminA = 800;
                vitaminC = 100;
                calcium = 800;
                sodium = 1500;
                iron = 10;
                fats = 12.9;
            }else if(age>=50 && age<=64){
                energy = 2200;
                protein = 60;
                dietaryFiber = 30;
                vitaminA = 750;
                vitaminC = 100;
                calcium = 750;
                sodium = 1500;
                iron = 10;
                fats = 10.4;
            }else if(age>=65 && age<=74){
                energy = 2000;
                protein = 60;
                dietaryFiber = 25;
                vitaminA = 700;
                vitaminC = 100;
                calcium = 700;
                sodium = 1300;
                iron = 9;
                fats = 8.2;
            }else{
                energy = 1900;
                protein = 60;
                dietaryFiber = 25;
                vitaminA = 700;
                vitaminC = 100;
                calcium = 700;
                sodium = 1100;
                iron = 9;
                fats = 5.9;
            }
        }
        else { // 여자
            if(age<=8){
                energy = 1500;
                protein = 35;
                dietaryFiber = 20;
                vitaminA = 400;
                vitaminC = 50;
                calcium = 700;
                sodium = 1200;
                iron = 9;
                fats = 7.8;
            }else if(age>=9 && age<=11){
                energy = 1800;
                protein = 45;
                dietaryFiber = 25;
                vitaminA = 550;
                vitaminC = 70;
                calcium = 800;
                sodium = 1500;
                iron = 10;
                fats = 10.1;
            }else if(age>=12 && age<=14){
                energy = 2000;
                protein = 55;
                dietaryFiber = 25;
                vitaminA = 650;
                vitaminC = 90;
                calcium = 900;
                sodium = 1500;
                iron = 16;
                fats = 10.2;
            }else if(age>=15 && age<=18){
                energy = 2000;
                protein = 55;
                dietaryFiber = 25;
                vitaminA = 650;
                vitaminC = 100;
                calcium = 800;
                sodium = 1500;
                iron = 14;
                fats = 11.1;
            }else if(age>=19 && age<=29){
                energy = 2000;
                protein = 55;
                dietaryFiber = 20;
                vitaminA = 650;
                vitaminC = 100;
                calcium = 700;
                sodium = 1500;
                iron = 14;
                fats = 11.2;
            }else if(age>=30 && age<=49){
                energy = 1900;
                protein = 50;
                dietaryFiber = 20;
                vitaminA = 650;
                vitaminC = 100;
                calcium = 700;
                sodium = 1200;
                iron = 14;
                fats = 9.7;
            }else if(age>=50 && age<=64){
                energy = 1700;
                protein = 50;
                dietaryFiber = 20;
                vitaminA = 600;
                vitaminC = 100;
                calcium = 800;
                sodium = 1500;
                iron = 8;
                fats = 8.2;
            }else if(age>=65 && age<=74){
                energy = 1600;
                protein = 50;
                dietaryFiber = 20;
                vitaminA = 600;
                vitaminC = 100;
                calcium = 800;
                sodium = 1300;
                iron = 8;
                fats = 5.5;
            }else{
                energy = 1500;
                protein = 50;
                dietaryFiber = 20;
                vitaminA = 600;
                vitaminC = 100;
                calcium = 800;
                sodium = 1100;
                iron = 7;
                fats = 3.4;
            }
        }

        Nutrient nutrient = new Nutrient(energy, carbohydrates, protein, dietaryFiber, calcium, sodium, iron,fats, vitaminA, vitaminC);
        member.createNutrient(nutrient);

        nutrientRepository.save(nutrient);

    }

    // 영양 정보 조회
    public NutrientResponse getNutrient(String email) {
        Member member =memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(ErrorCode.EMAIL_NOT_FOUND));

        Long id = member.getId();
        Nutrient nutrient = nutrientRepository.findById(id).orElseThrow(() -> new NutrientException(ErrorCode.NUTRIENT_NOT_FOUND));

        return new NutrientResponse(nutrient);
    }
}
