package kr.ac.korea.anniversary.config

import kr.ac.korea.anniversary.global.CustomException
import kr.ac.korea.anniversary.global.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> {
        logger.error(e.stackTraceToString())
        return ResponseEntity(
            ErrorResponse(
                error = e.msg,
            ),
            e.httpStatus,
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        logger.error(e.stackTraceToString())
        return ResponseEntity(
            ErrorResponse(
                error = "알 수 없는 에러가 발생했습니다",
            ),
            HttpStatus.INTERNAL_SERVER_ERROR,
        )
    }
}
