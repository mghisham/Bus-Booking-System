package com.hm.busbookingsystem.model.request

data class BookingRequest(
    val busNumber: String,
    val bookingDate: String,
    val source: String,
    val destination: String,
    val totalSeats: Int
)