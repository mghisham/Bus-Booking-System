package com.hm.busbookingsystem.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Bus(
    @Id val busNumber: String,
    val busName: String,
    val travelCompany: String,
    val capacity: Int,
    var availableSeats: Int,
    val source: String,
    val destination: String
)