package com.jwt.test.jwtoauth2.user.domain.repository

import com.jwt.test.jwtoauth2.user.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository:JpaRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
    fun existsByEmail(email: String): Boolean
}