package com.hm.busbookingsystem.controller

import com.hm.busbookingsystem.model.Booking
import com.hm.busbookingsystem.service.BookingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/booking-system")
class BookingController(private val bookingService: BookingService) {

    @PostMapping("/book")
    fun bookBus(@RequestBody request: BookingRequest): ResponseEntity<Booking> {
        val booking = bookingService.bookBus(
            busNumber = request.busNumber,
            bookingDate = LocalDate.parse(request.bookingDate),
            source = request.source,
            destination = request.destination,
            totalSeats = request.totalSeats
        )
        return ResponseEntity.ok(booking)
    }

    data class BookingRequest(
        val busNumber: String,
        val bookingDate: String,
        val source: String,
        val destination: String,
        val totalSeats: Int
    )
}