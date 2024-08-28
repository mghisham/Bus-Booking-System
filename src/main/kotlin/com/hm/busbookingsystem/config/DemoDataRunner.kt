package com.hm.busbookingsystem.config

import com.hm.busbookingsystem.model.Customer
import com.hm.busbookingsystem.repository.CustomerRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component


@Component
class DemoDataRunner(private val repo: CustomerRepository) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val customer = Customer(
            phone = "0123456789",
            fname = "test",
            lname = "test",
            password = "password"
        )
        repo.save(customer)
    }
}

