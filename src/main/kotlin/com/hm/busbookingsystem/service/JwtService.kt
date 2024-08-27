package com.hm.busbookingsystem.service

import com.hm.busbookingsystem.extensions.safeCall
import com.hm.busbookingsystem.model.Customer
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtService {
    @Value("\${security.jwt.secret-key}")
    private val secretKey: String? = null

    @Value("\${security.jwt.expiration-time}")
    val expirationTime: Long = 0

    fun extractUsername(token: String): String? {
        return extractClaim(token) { it.subject }
    }

    fun <T> extractClaim(token: String, claimResolver: (Claims) -> T): T? = safeCall {
        val claims = extractAllClaims(token)
        claimResolver(claims)
    }

    fun generateToken(userDetails: Customer): String {
        return generateToken(HashMap(), userDetails)
    }

    fun generateToken(extraClaims: Map<String?, Any?>, userDetails: Customer): String {
        return buildToken(extraClaims, userDetails, expirationTime)
    }

    private fun buildToken(
        extraClaims: Map<String?, Any?>,
        userDetails: Customer,
        expiration: Long
    ): String {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.phone)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username) && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = extractExpiration(token) ?: return true
        return expiration.before(Date())
    }

    private fun extractExpiration(token: String): Date? {
        return extractClaim(token) { it.expiration }
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parserBuilder()
            .setSigningKey(signInKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    private val signInKey: Key
        get() = safeCall {
            val keyBytes = Decoders.BASE64.decode(secretKey)
            Keys.hmacShaKeyFor(keyBytes)
        }
}