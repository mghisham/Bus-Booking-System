package com.hm.busbookingsystem.extensions

import com.hm.busbookingsystem.GenericException

fun <T> safeCall(message: String = "Something is broken!", block: () -> T): T = try {
    block.invoke()
} catch (e: Exception) {
    e.printStackTrace()
    throw GenericException(message, e)
}