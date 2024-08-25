package com.hm.busbookingsystem.service

import com.hm.busbookingsystem.model.Bus
import com.hm.busbookingsystem.repository.BusRepository
import org.springframework.stereotype.Service

@Service
class BusService(private val busRepository: BusRepository) {

    fun getAllBuses(): List<Bus> = busRepository.findAll()

    fun addBus(bus: Bus): Bus = busRepository.save(bus)

    fun updateBus(bus: Bus): Bus = busRepository.save(bus)

    fun searchBuses(source: String, destination: String): List<Bus> =
        busRepository.findBySourceAndDestination(source, destination)
}