package com.foody.refrigerators.repository;

import com.foody.refrigerators.dto.request.IngredientCSV;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class IngredientJDBCRepositoryImpl implements IngredientJDBCRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public void bulkInsert(List<IngredientCSV> ingredients) {
        int batchSize = 1000;
        for (int i = 0; i < ingredients.size(); i += batchSize) {
            List<IngredientCSV> batch = ingredients.subList(i, Math.min(i + batchSize, ingredients.size()));
            batchInsert(batch);

            if(i + batchSize < ingredients.size()) {
                log.info("Inserted {} out of {} ingredients", i + batchSize, ingredients.size());
            }
        }

    }

    @Override
    public boolean isExistsData() {
        String sql = "SELECT count(*) FROM ingredient WHERE id = ?";

        int count = jdbcTemplate.queryForObject(sql, new Object[]{2521}, Integer.class);
        return count > 0;
    }

    private void batchInsert(List<IngredientCSV> ingredients) {
        String sql = "INSERT INTO ingredient (id, ingredient_name, ingredient_type, ingredient_category_id, icon_img) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, ingredients, 1000, (ps, ingredient) -> {
            ps.setLong(1, ingredient.getIngredientId());
            ps.setString(2, ingredient.getIngredientName());
            ps.setInt(3, ingredient.getIngredientType());
            ps.setLong(4, ingredient.getIngredientCategoryId());
            ps.setString(5, ingredient.getIconImg());
        });
    }


}
