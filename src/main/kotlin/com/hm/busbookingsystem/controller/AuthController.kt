package com.hm.busbookingsystem.controller

import com.hm.busbookingsystem.model.Authentication
import com.hm.busbookingsystem.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/booking-system")
class AuthController(private val authService: AuthService) {

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody request: AuthRequest): ResponseEntity<Authentication> {
        return ResponseEntity.ok(authService.authenticate(request.phone, request.password))
    }

    data class AuthRequest(
        val phone: String,
        val password: String
    )
}