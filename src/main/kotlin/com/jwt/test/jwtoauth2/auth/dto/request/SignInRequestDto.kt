package com.jwt.test.jwtoauth2.auth.dto.request

data class SignInRequestDto(
    val name: String = "",
    val userName: String = "",
    val password: String = ""
)
