package com.jwt.test.jwtoauth2.auth.domain.model

import com.jwt.test.jwtoauth2.boot.domain.model.BaseEntity
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import org.hibernate.annotations.DynamicInsert
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_auth_token")
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Getter
class AuthToken(
    @Id
    val seq: String? = null,
    val userSeq:Long? = null,
    var accessToken: String? = null,
    val refreshToken: String? = null
): BaseEntity() {
    fun updateAccessToken(newAccessToken: String) {
        this.accessToken = newAccessToken
    }
}