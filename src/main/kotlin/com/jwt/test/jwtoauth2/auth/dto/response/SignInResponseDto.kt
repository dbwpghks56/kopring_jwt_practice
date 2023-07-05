package com.jwt.test.jwtoauth2.auth.dto.response

data class SignInResponseDto(
    val accessToken: String= "",
    val refreshToken: String= "",
    val type: String? = "Bearer",
    val userSeq: Long = 0L,
    val userName: String= "",
    val roles: Set<String> = HashSet<String>()
)
