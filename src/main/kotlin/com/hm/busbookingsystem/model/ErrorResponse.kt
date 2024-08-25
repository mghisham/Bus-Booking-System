package com.hm.busbookingsystem.model

data class ErrorResponse(
    val status: String,
    val code: Int,
    val message: String
)
