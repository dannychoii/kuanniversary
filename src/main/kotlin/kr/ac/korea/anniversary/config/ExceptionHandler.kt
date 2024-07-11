package kr.ac.korea.anniversary.config

import kr.ac.korea.anniversary.global.CustomException
import kr.ac.korea.anniversary.global.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                error = e.msg,
            ),
            e.httpStatus,
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                error = "알 수 없는 에러가 발생했습니다",
            ),
            HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }
}
