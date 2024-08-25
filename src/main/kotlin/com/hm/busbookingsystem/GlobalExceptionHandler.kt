package com.hm.busbookingsystem

import com.hm.busbookingsystem.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(status = "Failed", code = e.status.value(), message = e.message),
            e.status
        ).also {
            e.printStackTrace()
        }
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                status = "Something went wrong!",
                code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = e.message.orEmpty()
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        ).also {
            e.printStackTrace()
        }
    }

    @ExceptionHandler(RequestFailedBecauseOf::class)
    fun handleRequestFailedBecauseOfException(e: RequestFailedBecauseOf): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                status = "Your request is failed, Please try again.",
                code = HttpStatus.FORBIDDEN.value(),
                message = e.message.orEmpty()
            ),
            HttpStatus.FORBIDDEN
        ).also {
            e.printStackTrace()
        }
    }

    @ExceptionHandler(AuthException::class)
    fun handleAuthException(e: AuthException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                status = "Authentication failed", code = HttpStatus.UNAUTHORIZED.value(), message = e.message
            ),
            HttpStatus.UNAUTHORIZED
        ).also {
            e.printStackTrace()
        }
    }
}

class NotFoundException(val status: HttpStatus, override val message: String) : Exception()
class AuthException(override val message: String) : Exception(message)
class RequestFailedBecauseOf(message: String) : Exception(message)
class GenericException(message: String, e: Exception) : Exception(message, e)