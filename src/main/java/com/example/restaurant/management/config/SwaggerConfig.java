package com.example.restaurant.management.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Customer Feedback SaaS API")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Le Xuan Dung")
                                .email("lxd0294@gmail.com")));
    }
}