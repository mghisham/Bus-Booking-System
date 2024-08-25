package com.hm.busbookingsystem.security

import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    fun generateAccessToken(phone: String): String {
        return Jwts.builder()
            .subject(phone)
            .issuer(ISSUER)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + EXPIRE_DURATION))
            .compact()
    }

    companion object {
        private const val ISSUER = "Assessment"
        private const val EXPIRE_DURATION: Long = (24 * 60 * 60 * 1000).toLong() // 24 hour
    }
}