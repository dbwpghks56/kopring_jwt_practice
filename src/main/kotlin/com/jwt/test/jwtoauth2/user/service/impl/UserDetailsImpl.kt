package com.jwt.test.jwtoauth2.user.service.impl

import com.jwt.test.jwtoauth2.auth.role.ERole
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.ToString
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@ToString
@NoArgsConstructor
class UserDetailsImpl(
     val seq:Long? = null,
     val userName: String? = null,
     val email:String? = null,
     private val password:String? = null,
     val name:String? = null,
     val eRole: ERole? = null
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val auth = ArrayList<GrantedAuthority>()
        auth.add(SimpleGrantedAuthority(eRole?.name))
        return auth
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