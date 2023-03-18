package com.app.auth.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class AppConfiguration {
	
	@Value("${spring.application.description:description}")
    String applicationDescription;

    @Value("${spring.application.name:name}")
    String applicationName;

    @Value("${service.properties.version:v0.1}")
    String applicationVersion;

    @Value("${service.properties.terms-of-service-url:terms-of-service-url}")
    String termsOfServiceUrl;

    @Value("${service.properties.license-name:license-name}")
    String licenseName;

    @Value("${service.properties.license-url:license-url}")
    String licenseUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "Bearer Token";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title(applicationName)
                        .version(applicationVersion)
                        .description(applicationDescription)
                        .termsOfService(termsOfServiceUrl)
                        .license(new License().name(licenseName).url(licenseUrl)));
    }
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

}
