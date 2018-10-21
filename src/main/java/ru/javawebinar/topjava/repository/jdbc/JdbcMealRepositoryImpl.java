package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_meals")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id",             meal.getId())
                .addValue("calories",       meal.getCalories())
                .addValue("description",    meal.getDescription())
                .addValue("dateTime",       meal.getDateTime())
                .addValue("user_id",         userId);

        if(meal.isNew()){
            Number newKey = insertMeal.executeAndReturnKey(map);

            meal.setId(newKey.intValue());
        }else{

            if(namedParameterJdbcTemplate.update(
                    "UPDATE user_meals SET calories=:calories, description=:description, dateTime=:dateTime WHERE id=:id AND user_id=:user_id",
                    map) == 0){
                return null;
            }

        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return(jdbcTemplate.update("DELETE from user_meals WHERE id=? AND user_id=?", id, userId)
                != 0);
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> user_meals = jdbcTemplate.query("SELECT * FROM user_meals WHERE id=? AND user_id=?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(user_meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> user_meals = jdbcTemplate.query("SELECT * FROM user_meals WHERE user_id=? ORDER BY dateTime DESC", ROW_MAPPER, userId);
        return user_meals;
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Meal> user_meals = jdbcTemplate.query("SELECT * FROM user_meals WHERE user_id=? AND dateTime BETWEEN ? AND ? ORDER BY dateTime DESC", ROW_MAPPER, userId, startDate, endDate);
        return user_meals;
    }
}
