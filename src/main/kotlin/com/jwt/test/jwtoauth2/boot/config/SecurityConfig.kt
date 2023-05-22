package com.jwt.test.jwtoauth2.boot.config

import com.jwt.test.jwtoauth2.auth.filter.AuthJwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
class SecurityConfig(
    private val authJwtFilter: AuthJwtFilter,
    private val corsConfig: CorsConfig
) {
    companion object {
        private val PERMIT_URL_ARRAY = arrayOf<String>(
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
        )
    }

    @Bean
    fun configure(
        http: HttpSecurity
    ):SecurityFilterChain {
        http
            .cors().configurationSource(corsConfig.corsFilter())
            .and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .formLogin().disable()
            .httpBasic().disable()
            .authorizeHttpRequests()
            .antMatchers(*PERMIT_URL_ARRAY).permitAll()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers("/**/public/**").permitAll()
            .antMatchers("/user/**").permitAll()
            .antMatchers("/**/adm/**").hasAnyRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(authJwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling()

        return http.build()
    }

    @Bean
    fun webSecurityCustomizer() : WebSecurityCustomizer {
        return WebSecurityCustomizer { web ->
            web.ignoring().antMatchers("/resources/**")
        }
    }

    @Bean
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ) : AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}