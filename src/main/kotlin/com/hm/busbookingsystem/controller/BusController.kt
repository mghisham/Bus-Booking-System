package com.hm.busbookingsystem.controller

import com.hm.busbookingsystem.model.Bus
import com.hm.busbookingsystem.service.BusService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/booking-system")
class BusController(private val busService: BusService) {

    @GetMapping("/bus")
    fun getAllBuses(): ResponseEntity<List<Bus>> {
        return ResponseEntity.ok(busService.getAllBuses())
    }

    @PostMapping("/bus")
    fun addBus(@RequestBody bus: Bus): ResponseEntity<Bus> {
        return ResponseEntity.ok(busService.addBus(bus))
    }

    @PutMapping("/bus")
    fun updateBus(@RequestBody bus: Bus): ResponseEntity<Bus> {
        return ResponseEntity.ok(busService.updateBus(bus))
    }

    @GetMapping("/search")
    fun searchBuses(
        @RequestParam source: String,
        @RequestParam destination: String
    ): ResponseEntity<List<Bus>> {
        return ResponseEntity.ok(busService.searchBuses(source, destination))
    }
}