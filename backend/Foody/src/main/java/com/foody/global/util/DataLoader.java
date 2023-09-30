package com.foody.global.util;

import com.foody.mbti.entity.Mbti;
import com.foody.mbti.repository.MbtiRepository;
import com.foody.member.entity.Member;
import com.foody.member.repository.MemberRepository;
import com.foody.recipe.entity.Recipe;
import com.foody.recipe.repository.RecipeCustomRepository;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader {

    private final RecipeCustomRepository recipeCustomRepository;
    private final MemberRepository memberRepository;
    private final MbtiRepository mbtiRepository;

    // 사용자 기반 협업 필터링을 위해 랜덤 회원 생성
    public Member createRandomMember(int x, Mbti mbti) {
        Random rand = new Random();

        String nickname = "dummyUser" + x;
        double height = 140.0 + rand.nextInt(50); // 140cm ~ 190cm
        double weight = 40.0 + rand.nextInt(80);  // 40kg ~ 120kg
        int gender = x % 2 == 0? 1 : 2;             // 1 or 2
        int age = 10 + rand.nextInt(60);          // 10 ~ 70 years
        int activityLevel = x % 3 == 0? 1 : (x % 3 == 1)? 2 : 3;      // 1 ~ 3
        String profileImg = "dummyImage" + x;

        return new Member(nickname, height, weight, gender, age, activityLevel, profileImg, null, mbti);
    }

    public Mbti createRandomMbti() {
        Random rand = new Random();


        // 종류별
        int koreanMainDish = rand.nextInt(100); // 한식 메인디쉬
        int westernMainDish = rand.nextInt(100); // 양식 메인디쉬
        int sideDish = rand.nextInt(100); // 밑반찬
        int dessert = rand.nextInt(100); // 디저트

        // 상황별
        int dailyFood = rand.nextInt(100); // 일상
        int festivalFood = rand.nextInt(100); // 접대
        int convenienceFood = rand.nextInt(100); // 간편식
        int snackFood = rand.nextInt(100); // 술(안주)
        int etcFood = rand.nextInt(100); // 기타

        // 재료별
        int meat = rand.nextInt(100); // 고기류
        int vegetableSeafood = rand.nextInt(100); // 채소/해물류
        int processedFood = rand.nextInt(100); // 가공식품류
        int healthFood = rand.nextInt(100); // 건강류
        int grain = rand.nextInt(100); // 주식류

        // 방법별
        int lowCook = rand.nextInt(100); // 저온조리
        int highCook = rand.nextInt(100); // 고온조리
        int waterCook = rand.nextInt(100); // 수분조리
        int rawCook = rand.nextInt(100); // 날것
        int etcCook = rand.nextInt(100); // 기타

        return new Mbti(koreanMainDish, westernMainDish, sideDish, dessert, dailyFood, festivalFood, convenienceFood, snackFood, etcFood, meat, vegetableSeafood, processedFood, healthFood, grain, lowCook, highCook, waterCook, rawCook, etcCook);
    }
    public List<Recipe> readRecipesFromCSV(Reader reader) throws IOException {
        ColumnPositionMappingStrategy<Recipe> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Recipe.class);
        String[] memberFieldsToBindTo = {
            "id", "name", "ingredient", "description", "url", "difficulty", "servers",
            "foodMethod", "foodSituation", "foodIngredients", "foodTypes", "energy", "carbohydrates",
            "protein", "dietaryFiber", "calcium", "sodium", "iron", "fats", "vitaminA", "vitaminC"
        };
        strategy.setColumnMapping(memberFieldsToBindTo);

        CsvToBean<Recipe> csvToBean = new CsvToBeanBuilder<Recipe>(reader)
            .withMappingStrategy(strategy)
            .withSkipLines(1)
            .withType(Recipe.class)
            .build();

        return csvToBean.parse();
    }

    @Bean
    public CommandLineRunner dataLoad() {
        return (args) -> {
            boolean exists = recipeCustomRepository.isExistsData();
            ClassPathResource resource = new ClassPathResource("recipe/foody_recipe.csv");

            // InputStream을 사용하여 파일을 읽음
            try (InputStream inputStream = resource.getInputStream();
                Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                if (!exists) {
                    List<Recipe> recipes = readRecipesFromCSV(reader);
                    recipeCustomRepository.bulkInsert(recipes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    public CommandLineRunner memberDataLoad(MemberRepository memberRepository, MbtiRepository mbtiRepository) {
        return (args) -> {
            long count = memberRepository.count();
            if (count == 0) {
                for (int i = 1; i <= 1000; i++) {
                    Mbti mbti = createRandomMbti();
                    mbtiRepository.save(mbti); // 먼저 Mbti를 저장

                    Member member = createRandomMember(i, mbti); // 저장된 Mbti를 사용하여 Member 생성
                    memberRepository.save(member);
                }
            }
        };
    }

}
