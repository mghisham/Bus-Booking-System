package com.hm.busbookingsystem.security

import com.hm.busbookingsystem.GenericException
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
//            .signWith()
            .compact()
    }

    fun isTokenValid(token: String) {
        try {
            val jwtParser = Jwts.parser().build()
            jwtParser.parse(token)
        } catch (e: Exception) {
            throw GenericException("Could not verify JWT token integrity!", e)
        }
    }

    companion object {
        private const val ISSUER = "SecretAssessment"
        private const val EXPIRE_DURATION: Long = (24 * 60 * 60 * 1000).toLong() // 24 hour
    }
}