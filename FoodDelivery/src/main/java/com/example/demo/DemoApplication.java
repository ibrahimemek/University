package com.example.demo;


import com.example.demo.model.Restaurant;
import com.example.demo.repository.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.sql.rowset.JdbcRowSet;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.util.List;


@SpringBootApplication
@RestController
public class DemoApplication implements CommandLineRunner
{
	@Autowired
	private JdbcTemplate jdbcTemplate;


	public static void main(String[] args)
	{
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{

//		FoodController controller = new FoodController();
//		Food food = new Food();
//		food.setDescription("deneme123");
//		food.setName("food");
//		food.setPrice(new BigDecimal(3.10));
//		food.setId(123455);
//		food.setRestaurant_id(111111111);
//		controller.createFood(food);
//		String sql = ("SELECT * FROM Food");
//		List<Food> results = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Food.class));
//		results.forEach(System.out::println);
	}



}
