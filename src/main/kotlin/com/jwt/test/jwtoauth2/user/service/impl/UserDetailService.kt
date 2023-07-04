package com.jwt.test.jwtoauth2.user.service.impl

import com.jwt.test.jwtoauth2.boot.exception.RestException
import com.jwt.test.jwtoauth2.user.domain.model.User
import com.jwt.test.jwtoauth2.user.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailService(
    private val userRepository: UserRepository
): UserDetailsService{
    override fun loadUserByUsername(username: String?): UserDetails {
        val user: User = userRepository.findByEmail(username?: "").orElseThrow {
            throw UsernameNotFoundException("해당유저를 찾을 수 없습니다.")
        }

        val userDetailsImpl: UserDetailsImpl = user.toUserDetailsImpl()

        return userDetailsImpl
    }

}