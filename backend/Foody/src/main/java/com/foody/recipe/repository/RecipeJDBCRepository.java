package com.foody.recipe.repository;

import com.foody.recipe.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@RequiredArgsConstructor
public class RecipeJDBCRepository implements RecipeCustomRepository{

    private final JdbcTemplate jdbcTemplate;
    @Override
    public void bulkInsert(List<Recipe> recipes) {
        batchInsert(1000, recipes);
    }

    @Override
    public boolean isExistsData() {
        // 128671는 1번째 데이터로, 없으면 insert 진행
        String sql = "SELECT count(*) FROM recipe WHERE id = ?";

        int count = jdbcTemplate.queryForObject(sql, new Object[]{128671}, Integer.class);
        return count > 0;
    }

    private void batchInsert(int batchSize, List<Recipe> recipes) {
        String sql = "INSERT INTO recipe (recipe_id, name, ingredients, recipe, image_url, difficulty, servers, " +
                "food_preparation_methods, food_situations, food_ingredients, food_types) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, recipes, batchSize, (ps, recipe) -> {
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
