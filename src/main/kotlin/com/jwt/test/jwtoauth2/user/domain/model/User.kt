package com.jwt.test.jwtoauth2.user.domain.model

import com.jwt.test.jwtoauth2.auth.role.ERole
import com.jwt.test.jwtoauth2.boot.domain.model.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "tb_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val seq:Long? = null,
    val userName: String? = null,
    val email:String? = null,
    val password:String? = null,
    val name:String? = null,
    val eRole: ERole? = null,
):BaseEntity() {
}