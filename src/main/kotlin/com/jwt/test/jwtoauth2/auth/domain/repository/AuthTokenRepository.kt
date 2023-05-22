package com.jwt.test.jwtoauth2.auth.domain.repository

import com.jwt.test.jwtoauth2.auth.domain.model.AuthToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AuthTokenRepository: JpaRepository<AuthToken, String> {
    fun findBySeq(fakeRefreshToken: String): Optional<AuthToken>
    fun existsByAccessToken(accessToken: String): Boolean
    fun existsByUserSeq(userSeq: Long): Boolean
    fun existsByAccessTokenAndSeq(accessToken: String, userSeq: Long): Boolean
    fun existsBySeq(fakeRefreshToken: String): Boolean
}