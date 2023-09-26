package com.foody.recipe.repository;

import com.foody.recipe.entity.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RecipeJDBCRepository implements RecipeCustomRepository{

    private final JdbcTemplate jdbcTemplate;
    @Override
    public void bulkInsert(List<Recipe> recipes) {

        int batchSize = 1000;
        for (int i = 0; i < recipes.size(); i += batchSize) {
            List<Recipe> batch = recipes.subList(i, Math.min(i + batchSize, recipes.size()));
            batchInsert(batch);

            if (i % 5000 == 0 || i == recipes.size() - 1) {  // 5000번째나 마지막 배치의 경우 로그 출력
                log.info("Inserted {} out of {} recipes.", i + batch.size(), recipes.size());
            }
        }
    }

    @Override
    public boolean isExistsData() {
        // 128671는 1번째 데이터로, 없으면 insert 진행
        String sql = "SELECT count(*) FROM recipe WHERE id = ?";

        int count = jdbcTemplate.queryForObject(sql, new Object[]{128671}, Integer.class);
        return count > 0;
    }

    private void batchInsert(List<Recipe> recipes) {
        String sql = "INSERT INTO recipe (id, name, ingredient, description, url, difficulty, servers, " +
                "food_method, food_situation, food_ingredients, food_types) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, recipes, 1000, (ps, recipe) -> {
            ps.setLong(1, recipe.getId());
            ps.setString(2, recipe.getName());
            ps.setString(3, recipe.getIngredient());
            ps.setString(4, recipe.getDescription());
            ps.setString(5, recipe.getUrl());
            ps.setString(6, recipe.getDifficulty());
            ps.setString(7, recipe.getServers());
            ps.setString(8, recipe.getFoodMethod());
            ps.setString(9, recipe.getFoodSituation());
            ps.setString(10, recipe.getFoodIngredients());
            ps.setString(11, recipe.getFoodTypes());
        });
    }
}
