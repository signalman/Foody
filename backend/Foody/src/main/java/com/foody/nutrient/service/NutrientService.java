package com.foody.nutrient.service;

import com.foody.food.entity.Food;
import com.foody.global.exception.ErrorCode;
import com.foody.mealplan.entity.Meal;
import com.foody.mealplan.entity.MealPlan;
import com.foody.mealplan.entity.MealType;
import com.foody.mealplan.exception.MealPlanException;
import com.foody.mealplan.repository.MealPlanRepository;
import com.foody.mealplan.service.MealPlanService;
import com.foody.member.entity.Member;
import com.foody.member.exception.MemberException;
import com.foody.member.repository.MemberRepository;
import com.foody.nutrient.dto.response.NutrientByTypeResponse;
import com.foody.nutrient.dto.response.NutrientResponse;
import com.foody.nutrient.entity.Nutrient;
import com.foody.nutrient.exception.NutrientException;
import com.foody.nutrient.repository.NutrientRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class NutrientService {

    private final MemberRepository memberRepository;
    private final NutrientRepository nutrientRepository;
    private final MealPlanRepository mealPlanRepository;

    // 1일 권장 영양 정보 생성
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

    // 1일 총 영양 정보 조회
    @Transactional
    public NutrientResponse getNutrient(String email) {
        Member member =memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(ErrorCode.EMAIL_NOT_FOUND));

        Nutrient nutrient = member.getNutrient();

        return new NutrientResponse(nutrient);
    }

    // 1일 끼니별 영양 정보 조회
    @Transactional
    public NutrientResponse getMealNutrient(String email, MealType mealType) {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(ErrorCode.EMAIL_NOT_FOUND));
        Nutrient nutrient = member.getNutrient();

        if(mealType.equals(MealType.BREAKFAST)){
            // 전체 양의 20퍼
            return calculate(2, nutrient);
        }
        else if(mealType.equals(MealType.LUNCH)) {
            // 전체 양의 30퍼
            return calculate(3, nutrient);
        }
        else if(mealType.equals(MealType.DINNER)) {
            // 전체 양의 30퍼
            return calculate(3, nutrient);
        }
        else {
            // 전체 양의 20퍼
            return calculate(2, nutrient);
        }
    }

    private NutrientResponse calculate(int ratio, Nutrient nutrient) {

        double energy = nutrient.getEnergy() * ((double) ratio /10);
        double carbohydrates = nutrient.getCarbohydrates() * ((double) ratio /10);
        double protein = nutrient.getProtein() * ((double) ratio /10);
        double dietaryFiber = nutrient.getDietaryFiber() * ((double) ratio /10);
        double calcium = nutrient.getCalcium() * ((double) ratio /10);
        double sodium = nutrient.getSodium() * ((double) ratio /10);
        double iron = nutrient.getIron() * ((double) ratio /10);
        double fats = nutrient.getFats() * ((double) ratio /10);
        double vitaminA = nutrient.getVitaminA() * ((double) ratio /10);
        double vitaminC = nutrient.getVitaminC() * ((double) ratio /10);

        return new NutrientResponse(energy, carbohydrates, protein, dietaryFiber, calcium, sodium, iron, fats, vitaminA, vitaminC);
    }

    private double[] calculateNeccessaryNutrient(List<Food> foodList, double[] arr) {

        for(Food food: foodList) { // 권장 영양소 총합(arr 배열)에서 먹은 것(foodList) 빼주기
            arr[0] -= food.getNutrient().getEnergy();
            arr[1] -= food.getNutrient().getCarbohydrates();
            arr[2] -= food.getNutrient().getProtein();
            arr[3] -= food.getNutrient().getDietaryFiber();
            arr[4] -= food.getNutrient().getCalcium();
            arr[5] -= food.getNutrient().getSodium();
            arr[6] -= food.getNutrient().getCalcium();
            arr[7] -= food.getNutrient().getSodium();
            arr[8] -= food.getNutrient().getVitaminA();
            arr[9] -= food.getNutrient().getVitaminC();
        }

        return arr;
    }

    private double[] addNutrient(double[] arr, Nutrient nutrient) {
        arr[0] = nutrient.getEnergy();
        arr[1] = nutrient.getCarbohydrates();
        arr[2] = nutrient.getProtein();
        arr[3] = nutrient.getDietaryFiber();
        arr[4] = nutrient.getCalcium();
        arr[5] = nutrient.getSodium();
        arr[6] = nutrient.getIron();
        arr[7] = nutrient.getFats();
        arr[8] = nutrient.getVitaminA();
        arr[9] = nutrient.getVitaminC();

        return arr;
    }

    // 추천용 부족 영양소 반환
    // +면 부족한 것, -면 더먹은 것
    @Transactional(readOnly = true)
    public Nutrient getNutrientForRecommendation(LocalDateTime recommendTime, String email) {
        // 함수 호출 시간대별 아=1, 점=2, 저=3, 야=4 분리
        LocalTime mealTime = LocalTime.of(recommendTime.getHour(), recommendTime.getMinute(),
            recommendTime.getSecond());
        LocalDate mealDate = LocalDate.of(recommendTime.getYear(), recommendTime.getMonth(),
            recommendTime.getDayOfMonth());
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(ErrorCode.EMAIL_NOT_FOUND));

        if(calcMealTime(mealTime) == 1) { // 아침
            return Nutrient.changeTypetoNutrient(getMealNutrient(email, MealType.BREAKFAST));
        }
        else if(calcMealTime(mealTime) == 2){ // 점심
            MealPlan mealPlan = mealPlanRepository.findByDateAndMemberId(mealDate, member.getId())
                                                  .orElseThrow(
                                                      () -> new MealPlanException(ErrorCode.MEAL_PLAN_NOT_FOUND));

            if(mealPlan.getBreakfast() != null){ // 아침 O
                // 부족 영양분 계산
                Meal breakfast = mealPlan.getBreakfast();
                Nutrient breakfastTmp = Nutrient.changeTypetoNutrient(getMealNutrient(email, MealType.BREAKFAST));
                Nutrient lunchTmp = Nutrient.changeTypetoNutrient(getMealNutrient(email, MealType.LUNCH));
                double[] arr = new double[10]; // 영양정보 저장용

                // 아침 + 점심 권장 영양소
                addNutrient(arr, breakfastTmp);
                addNutrient(arr, lunchTmp);

                List<Food> breakfastList = breakfast.getFoods();
                calculateNeccessaryNutrient(breakfastList, arr);

                return new Nutrient(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9]);
            }
            else{ // 아침 X
                return Nutrient.changeTypetoNutrient(getMealNutrient(email, MealType.LUNCH));
            }
        }
        else if(calcMealTime(mealTime) == 3) { // 저녁
            MealPlan mealPlan = mealPlanRepository.findByDateAndMemberId(mealDate, member.getId())
                                                  .orElseThrow(
                                                      () -> new MealPlanException(ErrorCode.MEAL_PLAN_NOT_FOUND));
            Nutrient breakfastTmp = Nutrient.changeTypetoNutrient(getMealNutrient(email, MealType.BREAKFAST));
            Nutrient lunchTmp = Nutrient.changeTypetoNutrient(getMealNutrient(email, MealType.LUNCH));
            Nutrient dinnerTmp = Nutrient.changeTypetoNutrient(getMealNutrient(email, MealType.DINNER));
            double[] arr = new double[10];

            if(mealPlan.getBreakfast() != null && mealPlan.getLunch() != null) { // 아침 O 점심 O
                // 아침 + 점심 + 저녁 권장 영양소
                addNutrient(arr, breakfastTmp);
                addNutrient(arr, lunchTmp);
                addNutrient(arr, dinnerTmp);

                // 부족 영양소 계산
                List<Food> breakfastList = mealPlan.getBreakfast().getFoods();
                List<Food> lunchList = mealPlan.getLunch().getFoods();
                calculateNeccessaryNutrient(breakfastList, arr);
                calculateNeccessaryNutrient(lunchList, arr);

                return new Nutrient(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9]);
            }
            else if(mealPlan.getBreakfast() != null && mealPlan.getLunch() == null) { // 아침 O 점심 X
                // 아침 + 저녁 권장 영양소
                addNutrient(arr, breakfastTmp);
                addNutrient(arr, dinnerTmp);

                List<Food> breakfastList = mealPlan.getBreakfast().getFoods();
                calculateNeccessaryNutrient(breakfastList, arr);

                return new Nutrient(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9]);
            }
            else if(mealPlan.getBreakfast() == null && mealPlan.getLunch() != null) { // 아침 X 점심 O
                // 점심 + 저녁 권장 영양소
                addNutrient(arr, breakfastTmp);
                addNutrient(arr, lunchTmp);

                List<Food> lunchList = mealPlan.getLunch().getFoods();
                calculateNeccessaryNutrient(lunchList, arr);

                return new Nutrient(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9]);
            }
            else { // 아침 X 점심 X
                return Nutrient.changeTypetoNutrient(getMealNutrient(email, MealType.DINNER));
            }
        }
        else { // 야식
            return Nutrient.changeTypetoNutrient(getMealNutrient(email, MealType.SNACK));
        }

    }

    // 해당 끼니의 먹은 영양소 반환
    @Transactional
    public NutrientResponse calcMealNutrient(String email, LocalDateTime localDateTime, MealType mealType) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(ErrorCode.EMAIL_NOT_FOUND));
        LocalDate mealDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(),
            localDateTime.getDayOfMonth());
        MealPlan mealplan = mealPlanRepository.findByDateAndMemberId(mealDate, member.getId())
                                              .orElseThrow(
                                                  () -> new MealPlanException(ErrorCode.MEAL_PLAN_NOT_FOUND));
        Meal meal;

        if(mealType.equals(MealType.BREAKFAST)){
            meal = mealplan.getBreakfast();
        }
        else if(mealType.equals(MealType.LUNCH)) {
            meal = mealplan.getLunch();
        }
        else if(mealType.equals(MealType.DINNER)) {
            meal = mealplan.getDinner();
        }
        else{
            meal = mealplan.getSnack();
        }

        List<Food> foods = meal.getFoods();
        double[] arr = new double[10];
        for(Food food: foods){
            arr[0] += food.getNutrient().getEnergy();
            arr[1] += food.getNutrient().getCarbohydrates();
            arr[2] += food.getNutrient().getProtein();
            arr[3] += food.getNutrient().getDietaryFiber();
            arr[4] += food.getNutrient().getCalcium();
            arr[5] += food.getNutrient().getSodium();
            arr[6] += food.getNutrient().getIron();
            arr[7] += food.getNutrient().getFats();
            arr[8] += food.getNutrient().getVitaminA();
            arr[9] += food.getNutrient().getVitaminC();
        }

        return new NutrientResponse(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5],arr[6],arr[7],arr[8],arr[9]);
    }

    private int calcMealTime(LocalTime time) {
        LocalTime breakfastStart = LocalTime.of(5,0,0);
        LocalTime breakfastEnd = LocalTime.of(10,0,0);
        LocalTime lunchStart = LocalTime.of(10,0,0);
        LocalTime lunchEnd = LocalTime.of(15,0,0);
        LocalTime dinnerStart = LocalTime.of(15,0,0);
        LocalTime dinnerEnd = LocalTime.of(22,0,0);

        if(!time.isBefore(breakfastStart) && !time.isAfter(breakfastEnd)){
            return 1;
        }
        else if(!time.isBefore(lunchStart) && !time.isAfter(lunchEnd)) {
            return 2;
        }
        else if(!time.isBefore(dinnerStart) && !time.isAfter(dinnerEnd)){
            return 3;
        }
        else {
            return 4;
        }
    }

    @Transactional
    public NutrientByTypeResponse getAllNutrient(String email) {
        Member member =memberRepository.findByEmail(email).orElseThrow(() -> new MemberException(ErrorCode.EMAIL_NOT_FOUND));

        Nutrient nutrient = member.getNutrient();
        NutrientResponse breakfast = calculate(2,nutrient);
        NutrientResponse lunch = calculate(3,nutrient);
        NutrientResponse dinner = calculate(3,nutrient);
        NutrientResponse snack = calculate(2,nutrient);

        return new NutrientByTypeResponse(breakfast,lunch,dinner,snack);
    }
}
