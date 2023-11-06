package com.sushchenko.mystictourismapp;

import com.ibm.icu.text.Transliterator;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class })
public class MysticTourismAppApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Bean
	public Transliterator transliterator() { return Transliterator.getInstance("Russian-Latin/BGN");}
	public static void main(String[] args) {
		SpringApplication.run(MysticTourismAppApplication.class, args);
	}

}
