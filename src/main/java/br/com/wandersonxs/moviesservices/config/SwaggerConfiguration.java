package br.com.wandersonxs.moviesservices.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Value("${application.api.title}")
    private String appApiTitle;

    @Value("${application.api.description}")
    private String appDescription;

    @Value("${application.api.version}")
    private String appVersion;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(appApiTitle)
                        .description(appDescription)
                        .version(appVersion));
    }

}
