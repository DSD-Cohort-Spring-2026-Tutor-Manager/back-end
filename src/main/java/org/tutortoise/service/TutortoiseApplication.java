package org.tutortoise.service;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Tutortoise API",
                version = "1.0",
                description = "API documentation for managing tutoring center"
        )
)

@SpringBootApplication
public class TutortoiseApplication {

  public static void main(String[] args) {
    SpringApplication.run(TutortoiseApplication.class, args);

  }

}