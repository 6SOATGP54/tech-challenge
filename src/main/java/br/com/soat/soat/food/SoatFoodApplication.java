package br.com.soat.soat.food;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "SOAT FOOD",
		description = "Documentation endpoints",
		version = "v1"))
@SpringBootApplication
public class SoatFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoatFoodApplication.class, args);
	}

}
