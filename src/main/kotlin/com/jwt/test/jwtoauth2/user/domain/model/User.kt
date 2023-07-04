package com.jwt.test.jwtoauth2.user.domain.model

import com.jwt.test.jwtoauth2.auth.domain.model.Role
import com.jwt.test.jwtoauth2.auth.role.ERole
import com.jwt.test.jwtoauth2.boot.domain.model.BaseEntity
import net.minidev.json.annotate.JsonIgnore
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
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tb_user_role",
        joinColumns = [JoinColumn(name = "user_seq")],
        inverseJoinColumns = [JoinColumn(name = "role_name")]
    )
    val roles: Set<Role> = HashSet()
):BaseEntity() {
}