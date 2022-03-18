package com.example.registrationsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Collections.singletonList(getContext()))
                .securitySchemes(Collections.singletonList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.registrationsystem"))
                .paths(PathSelectors.any())
                .build().apiInfo(metadata());
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
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder().title("Web App")
                .description("This project is a real website")
                .license("Apache version 2.0")
                .version("1.1.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
                .contact(new Contact("Digital One", "www.group.uz", "azizovmuhammaduz@gmail.com"))
                .build();
    }
}
