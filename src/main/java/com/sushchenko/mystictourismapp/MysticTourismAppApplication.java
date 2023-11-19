package com.sushchenko.mystictourismapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.icu.text.Transliterator;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.jackson.JsonNodeValueReader;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.geo.GeoJsonModule;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import static org.springframework.data.mongodb.core.geo.GeoJsonModule.geoJsonModule;

@EnableCaching
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class
//		, RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class
})
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
