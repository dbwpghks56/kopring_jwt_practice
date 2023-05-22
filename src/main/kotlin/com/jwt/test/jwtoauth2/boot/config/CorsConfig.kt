package com.jwt.test.jwtoauth2.boot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig {
    @Bean
    fun corsFilter() : CorsConfigurationSource {
        val source : UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        val corsConfiguration : CorsConfiguration = CorsConfiguration()

        corsConfiguration.allowCredentials = true
        corsConfiguration.addAllowedOriginPattern("*")
        corsConfiguration.addAllowedHeader("*")
        corsConfiguration.addAllowedMethod("*")

        source.registerCorsConfiguration("/api/**", corsConfiguration)
        source.registerCorsConfiguration("/**", corsConfiguration)

        return source
    }

}