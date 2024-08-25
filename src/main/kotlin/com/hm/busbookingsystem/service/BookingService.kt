package com.hm.busbookingsystem.service

import com.hm.busbookingsystem.RequestFailedBecauseOf
import com.hm.busbookingsystem.model.Booking
import com.hm.busbookingsystem.repository.BookingRepository
import com.hm.busbookingsystem.repository.BusRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val busRepository: BusRepository
) {

    fun bookBus(
        busNumber: String,
        bookingDate: LocalDate,
        source: String,
        destination: String,
        totalSeats: Int
    ): Booking {
        val bus = busRepository.findById(busNumber).orElseThrow { RequestFailedBecauseOf("Bus not found") }

        if (bus.availableSeats < totalSeats) {
            throw RequestFailedBecauseOf("Not enough seats available")
        }

        bus.availableSeats -= totalSeats
        busRepository.save(bus)

        val booking = Booking(
            busNumber = busNumber,
            bookingDate = bookingDate,
            source = source,
            destination = destination,
            totalSeats = totalSeats,
            bookingStatus = "CONFIRMED"
        )

        return bookingRepository.save(booking)
    }
}