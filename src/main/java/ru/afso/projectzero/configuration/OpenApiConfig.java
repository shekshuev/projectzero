package ru.afso.projectzero.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI projectZeroOpenApi() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("ProjectZero REST API")
                        .description("REST API for analytics system")
                        .version("1.0")
                        .contact(new Contact().name("Sergei Shekshuev").email("sergei.shekshuev@gmail.com"))
                        .license(new License().name("MIT").url("https://www.mit.edu/~amini/LICENSE.md")));
    }
}
