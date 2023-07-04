package com.jwt.test.jwtoauth2.auth.domain.model

import com.jwt.test.jwtoauth2.auth.role.ERole
import lombok.NoArgsConstructor
import lombok.ToString
import javax.persistence.*

@Entity
@ToString
@Table(name = "tb_role")
@NoArgsConstructor
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Enumerated(EnumType.STRING)
    val role: ERole
) {
}