package br.edu.ifpi.financialcontrol.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi(){
        GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder()
                .packagesToScan("br.edu.ifpi.financialcontrol")
                .pathsToMatch("/flow")
                .group("Flow")
                .build();
        return groupedOpenApi;
    }

    @Bean
    @OpenAPI30
    public OpenAPI customOpenApi(){
        OpenAPI openAPI = new OpenAPI();

        Contact contact = new Contact().url("https://github.com/henr1ck");
        contact.setName("GitHub");

        return openAPI.info(new Info()
                .title("Financial Control API")
                .contact(contact)
                .description("Documentation for Financial Control API")
                .version("1"));
    }
}
