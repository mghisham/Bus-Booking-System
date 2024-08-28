package com.hm.busbookingsystem.service

import com.hm.busbookingsystem.AuthException
import com.hm.busbookingsystem.NotFoundException
import com.hm.busbookingsystem.model.Customer
import com.hm.busbookingsystem.model.request.AuthRequest
import com.hm.busbookingsystem.repository.CustomerRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service


@Service
class AuthenticationService(private val customerRepository: CustomerRepository) {

    fun authenticate(input: AuthRequest): Customer {
        val customer = customerRepository.findByPhone(input.username) ?: throw NotFoundException(
            HttpStatus.UNAUTHORIZED,
            "User not found"
        )
        if (customer.password != input.password) {
            throw AuthException("Invalid password")
        }
        return customer
    }
}