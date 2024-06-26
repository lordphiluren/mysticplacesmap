package com.sushchenko.mystictourismapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication(
//		exclude = {DataSourceAutoConfiguration.class
//		, RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class
//}
)
public class MysticTourismAppApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(MysticTourismAppApplication.class, args);
	}

}
