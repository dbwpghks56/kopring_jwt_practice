package com.jwt.test.jwtoauth2.boot.config

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket

@Configuration
@RequiredArgsConstructor
class SwaggerConfig {
    @Bean
    fun api() : Docket {
        return Docket(
            DocumentationType.OAS_30
        )
            .securityContexts(listOf(securityContext()))
            .securitySchemes(listOf(apiKey()))
            .useDefaultResponseMessages(true)
            .apiInfo(appInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.jwt.test.jwtoauth2"))
            .paths(PathSelectors.any())
            .build()

    }

    private fun securityContext() : SecurityContext {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope:AuthorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOf(authorizationScope)

        return listOf(SecurityReference("Authorization", authorizationScopes))
    }

    private fun apiKey(): ApiKey {
        return ApiKey("Authorization", "Bearer", "header")
    }

    private fun appInfo() : ApiInfo {
        return ApiInfoBuilder()
            .title("jwt 및 oauth2 test Swagger")
            .description(" jwt 및 oauth2 test Swagger")
            .version("1.0.0+1")
            .build();
    }
}