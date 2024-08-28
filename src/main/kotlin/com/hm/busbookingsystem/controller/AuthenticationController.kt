package com.hm.busbookingsystem.controller

import com.hm.busbookingsystem.model.Authentication
import com.hm.busbookingsystem.model.Customer
import com.hm.busbookingsystem.model.request.AuthRequest
import com.hm.busbookingsystem.service.AuthenticationService
import com.hm.busbookingsystem.service.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/booking-system")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService,
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody authRequest: AuthRequest): ResponseEntity<Authentication> {
        /*authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
        )*/
        val authenticatedUser: Customer = authenticationService.authenticate(authRequest)
        val jwtToken = jwtService.generateToken(authenticatedUser)
        val loginResponse = Authentication(
            token = jwtToken,
            expiry = jwtService.expirationTime,
            status = "Success"
        )
        return ResponseEntity.ok(loginResponse)
    }
}