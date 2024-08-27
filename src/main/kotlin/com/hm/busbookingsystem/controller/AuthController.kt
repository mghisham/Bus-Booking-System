package com.hm.busbookingsystem.controller

import com.hm.busbookingsystem.GenericException
import com.hm.busbookingsystem.model.Authentication
import com.hm.busbookingsystem.security.JwtUtil
import com.hm.busbookingsystem.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/booking-system")
class AuthController {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody request: AuthRequest): ResponseEntity<*> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(request.username, request.password)
            )
        } catch (e: Exception) {
            throw GenericException("Incorrect phone or password", e)
        }

        val userDetails = userDetailsService.loadUserByUsername(request.username)
        val token = jwtUtil.generateToken(userDetails)
//        return ResponseEntity.ok(authService.authenticate(request.phone, request.password))
        return ResponseEntity.ok(Authentication(status = "Success", token))
    }

    data class AuthRequest(
        val username: String,
        val password: String
    )
}