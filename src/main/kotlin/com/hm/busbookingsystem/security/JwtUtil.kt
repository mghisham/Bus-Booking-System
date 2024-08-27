package com.hm.busbookingsystem.security

import com.hm.busbookingsystem.extensions.safeCall
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${jwt.expiration}")
    private var expirationInMillis: Long = 0

    fun generateAccessToken(phone: String): String {
        val key = Keys.hmacShaKeyFor(secret.toByteArray())
        return Jwts.builder()
            .setSubject(phone)
            .setIssuer(ISSUER)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expirationInMillis))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateToken(userDetails: UserDetails): String = safeCall(message = "Could not generate JWT token!") {
        val claims = hashMapOf<String, Any>()
        doGenerateToken(claims, userDetails.username)
    }

    private fun doGenerateToken(claims: HashMap<String, Any>, subject: String): String {
        val key = Keys.hmacShaKeyFor(secret.toByteArray())
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expirationInMillis))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getUserNameFromToken(token: String) = safeCall { token.userName }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean =
        safeCall(message = "Could not verify JWT token integrity!") {
            token.userName == userDetails.username && !token.isTokenExpired
        }

    private val String.isTokenExpired: Boolean
        get() {
            val expiration = getClaimFromToken(this) { it.expiration }
            return expiration.before(Date())
        }

    private val String.userName: String
        get() = getClaimFromToken(this) { it.subject }

    private fun <T> getClaimFromToken(token: String, claimResolver: (Claims) -> T): T {
        val claims = getClaims(token)
        return claimResolver.invoke(claims)
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder().setSigningKey(secret.toByteArray()).build().parseClaimsJws(token).body
    }

    companion object {
        private const val ISSUER = "SecretAssessment"
    }
}