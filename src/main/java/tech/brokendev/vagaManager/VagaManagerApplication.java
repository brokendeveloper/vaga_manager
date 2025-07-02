package tech.brokendev.vagaManager;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Vaga Manager",
		description = "API responsible for jobs management",
		version = "1.0"
))
public class VagaManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VagaManagerApplication.class, args);
	}

}
