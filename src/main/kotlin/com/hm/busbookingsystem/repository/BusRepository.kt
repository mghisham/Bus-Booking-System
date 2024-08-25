package com.hm.busbookingsystem.repository

import com.hm.busbookingsystem.model.Bus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BusRepository : JpaRepository<Bus, String> {
    fun findBySourceAndDestination(source: String, destination: String): List<Bus>
}