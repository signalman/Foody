package com.foody.food.repository;

import com.foody.food.entity.FoodSearch;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class FoodSearchJDBCRepositoryImpl implements FoodSearchJDBCRepository {

    private final JdbcTemplate jdbcTemplate;

    public void bulkInsert(List<FoodSearch> foodSearchList){
        int batchSize = 1000;
        for (int i = 0; i < foodSearchList.size(); i += batchSize) {
            List<FoodSearch> batch = foodSearchList.subList(i, Math.min(i + batchSize, foodSearchList.size()));
            batchInsert(batch);

            if (i % 5000 == 0 || i == foodSearchList.size() - 1) {  // 5000번째나 마지막 배치의 경우 로그 출력
                log.info("Inserted {} out of {} foodSearchList.", i + batch.size(), foodSearchList.size());
            }
        }
    }

    public boolean isExistsData(){
        String sql = "SELECT count(*) FROM food_search WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, new Object[]{7683}, Integer.class);
        return count > 0;
    }

    private void batchInsert(List<FoodSearch> foodSearchList) {

        String sql = "INSERT INTO food_search (id, name, energy, carbohydrates, protein, dietary_fiber, calcium, sodium, iron, fats, vitamina, vitaminc) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, foodSearchList, 1000, (ps, foodSearch) -> {
            ps.setLong(1, foodSearch.getId());
            ps.setString(2, foodSearch.getName());
            ps.setDouble(3, foodSearch.getEnergy());
            ps.setDouble(4, foodSearch.getCarbohydrates());
            ps.setDouble(5, foodSearch.getProtein());
            ps.setDouble(6, foodSearch.getDietaryFiber());
            ps.setDouble(7, foodSearch.getCalcium());
            ps.setDouble(8, foodSearch.getSodium());
            ps.setDouble(9, foodSearch.getIron());
            ps.setDouble(10, foodSearch.getFats());
            ps.setDouble(11, foodSearch.getVitaminA());
            ps.setDouble(12, foodSearch.getVitaminC());
        });
    }


}
