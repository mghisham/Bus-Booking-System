package com.hm.busbookingsystem.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Customer(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val phone: String,
    val fname: String,
    val lname: String,
    val password: String
) {
    constructor() : this(
        phone = "0123456789",
        fname = "admin",
        lname = "admin",
        password = "password"
    )
}
