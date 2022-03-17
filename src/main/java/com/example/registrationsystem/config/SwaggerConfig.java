package com.example.registrationsystem.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@Component
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Collections.singletonList(getContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("uz.group.app"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo());
    }

    private ApiKey apiKey(){
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext getContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }


    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("API Endpoints for Registration Web App")
                .description("This project is a real estate e-commerce website")
                .license("Apache version 2.0")
                .version("1.1.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
                .contact(new Contact("Digital One", "www.group.uz", "gm.azizovmuhammaduz@gmail.com.com"))
                .build();
    }
}
