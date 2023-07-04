package com.jwt.test.jwtoauth2.user.service.impl

import com.jwt.test.jwtoauth2.auth.role.ERole
import com.jwt.test.jwtoauth2.user.domain.model.User
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.ToString
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import kotlin.streams.toList

@ToString
@NoArgsConstructor
class UserDetailsImpl(
     val seq:Long? = null,
     val userName: String? = null,
     val email:String? = null,
     private val password:String? = null,
     val name:String? = null,
     val roles: List<ERole>? = null
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = mutableListOf<GrantedAuthority>()
        roles?.forEach { role ->
            authorities.add(SimpleGrantedAuthority(role.name))
        }
        return authorities
    }


    override fun getPassword(): String {
        return password ?: ""
    }

    override fun getUsername(): String {
        return email ?: ""
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
fun User.toUserDetailsImpl(): UserDetailsImpl {
    val rolesForResponse: List<ERole> = roles.map { it.role } ?: emptyList()

    return UserDetailsImpl(
        seq = seq,
        userName = userName,
        email = email,
        password = password,
        name = name,
        roles = rolesForResponse
    )
}