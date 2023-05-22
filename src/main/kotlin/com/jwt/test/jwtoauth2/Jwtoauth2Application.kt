package com.jwt.test.jwtoauth2

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class Jwtoauth2Application

fun main(args: Array<String>) {
	val springApplication: SpringApplication = SpringApplication(Jwtoauth2Application::class.java)
	springApplication.setLogStartupInfo(false)
	springApplication.run(*args)
}
