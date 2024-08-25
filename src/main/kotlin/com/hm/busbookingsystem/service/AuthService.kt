package com.hm.busbookingsystem.service

import com.hm.busbookingsystem.AuthException
import com.hm.busbookingsystem.NotFoundException
import com.hm.busbookingsystem.model.Authentication
import com.hm.busbookingsystem.model.Customer
import com.hm.busbookingsystem.repository.CustomerRepository
import com.hm.busbookingsystem.security.JwtUtil
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val customerRepository: CustomerRepository,
    private val jwtUtil: JwtUtil
) {

    init {
        customerRepository.save(Customer())
    }

    fun authenticate(phone: String, password: String): Authentication {
        val customer = customerRepository.findByPhone(phone)
            ?: throw NotFoundException(HttpStatus.UNAUTHORIZED, "Customer not found")
        if (customer.password != password) {
            throw AuthException("Invalid password")
        }

        val token = jwtUtil.generateAccessToken(phone)
        return Authentication(token = token, status = "Success")
    }
}