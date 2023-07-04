package com.jwt.test.jwtoauth2.auth.domain.repository

import com.jwt.test.jwtoauth2.auth.domain.model.Role
import com.jwt.test.jwtoauth2.auth.role.ERole
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RoleRepository: JpaRepository<Role, Long> {
    fun findByRole(role: ERole) : Optional<Role>
}