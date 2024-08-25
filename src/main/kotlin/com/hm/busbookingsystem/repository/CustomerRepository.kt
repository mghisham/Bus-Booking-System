package com.hm.busbookingsystem.repository

import com.hm.busbookingsystem.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Long> {
    fun findByPhone(phone: String): Customer?
}