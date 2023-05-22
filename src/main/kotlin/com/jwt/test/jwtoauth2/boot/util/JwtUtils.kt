package com.jwt.test.jwtoauth2.boot.util

import com.jwt.test.jwtoauth2.auth.domain.model.AuthToken
import com.jwt.test.jwtoauth2.auth.domain.repository.AuthTokenRepository
import com.jwt.test.jwtoauth2.boot.exception.RestException
import com.jwt.test.jwtoauth2.user.service.impl.UserDetailsImpl
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtDecoders
import org.springframework.stereotype.Component
import java.lang.Exception
import java.security.Key
import java.util.*
import javax.servlet.http.HttpServletResponse

@Component
class JwtUtils(
    @Value("\${jwt.secret}")
    val jwtSecret: String,
    private val authTokenRepository: AuthTokenRepository
) {
    fun getKey(): Key {
        val keys = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keys)
    }

    fun generateAccessToken(authentication: Authentication):String {
        val userDetailsImpl = authentication.principal as UserDetailsImpl
        return Jwts.builder()
            .setSubject(userDetailsImpl.username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 7)))
            .setIssuer("JwtTest")
            .signWith(getKey(), SignatureAlgorithm.HS512)
            .claim("name", userDetailsImpl.name)
            .compact()

    }

    fun generateRefreshToken(authentication: Authentication): String {
        val userDetailsImpl = authentication.principal as UserDetailsImpl
        return Jwts.builder()
            .setSubject(userDetailsImpl.username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 14)))
            .setIssuer("JwtTest")
            .signWith(getKey(), SignatureAlgorithm.HS512)
            .compact()
    }

    fun getUserNameFromAccessToken(token: String): String {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .body

            claims.subject
        } catch (e: ExpiredJwtException) {
            e.claims.subject
        }
    }

    fun getNameForAccessToken(token:String) : String{
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .body

            claims["name"] as String
        } catch (e: ExpiredJwtException) {
            e.claims.subject
        }
    }

    fun getAccessTokenFromBearer(rawAccessToken: String) :String {
        return rawAccessToken.replace("Bearer ", "")
    }

    fun validAccessToken(
        token: String,
        response: HttpServletResponse
    ) : Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)

            true
        } catch (e: Exception) {
            throw RestException(HttpStatus.BAD_REQUEST, e.message as String)
        }
    }

    fun validRefreshToken(fakeRefreshToken: String): Boolean {
        val authTokenEntity: AuthToken = authTokenRepository.findBySeq(fakeRefreshToken)
            .orElseThrow{
                throw RestException(HttpStatus.NOT_FOUND, "존재 X")
            }

        return try {
            Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(authTokenEntity.refreshToken)

            true
        } catch (e: Exception) {
            throw RestException(HttpStatus.BAD_REQUEST, e.message as String)
        }
    }
}














