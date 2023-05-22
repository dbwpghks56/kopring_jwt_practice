package com.jwt.test.jwtoauth2.auth.filter

import com.jwt.test.jwtoauth2.auth.domain.repository.AuthTokenRepository
import com.jwt.test.jwtoauth2.boot.exception.RestException
import com.jwt.test.jwtoauth2.boot.util.JwtUtils
import com.jwt.test.jwtoauth2.user.service.impl.UserDetailService
import com.jwt.test.jwtoauth2.user.service.impl.UserDetailsImpl
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthJwtFilter(
    private val jwtUtils: JwtUtils,
    private val userDetailService: UserDetailService,
    private val authTokenRepository: AuthTokenRepository,
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val accessToken: String? = getAccessToken(request)
            val requestPath: String = request.servletPath
            if (accessToken != null && jwtUtils.validAccessToken(accessToken, response)) {
                if (!authTokenRepository.existsByAccessToken(accessToken)) {
                    throw RestException(
                        HttpStatus.BAD_REQUEST,
                        "AccessToken이 DB 내 토큰과 일치하지 않습니다. 이전 사용자/로그아웃된 사용자, 혹은 조작된 토큰일 수 있습니다."
                    );
                }

                val userName: String = jwtUtils.getUserNameFromAccessToken(accessToken)

                val userDetailsImpl = userDetailService.loadUserByUsername(userName) as UserDetailsImpl
                val authenticationToken: UsernamePasswordAuthenticationToken =
                    UsernamePasswordAuthenticationToken(userDetailsImpl, null, userDetailsImpl.authorities)
                authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authenticationToken
            }

            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            println("${e.message} + ${e.cause}")
        }
    }

    fun getAccessToken(
        request: HttpServletRequest
    ): String? {
        val bearerToken:String? = request.getHeader(HttpHeaders.AUTHORIZATION)

        if(StringUtils.hasText(bearerToken) && bearerToken?.startsWith("Bearer ") == true) {
            return bearerToken.replace("Bearer ", "")
        }

        return null
    }
}