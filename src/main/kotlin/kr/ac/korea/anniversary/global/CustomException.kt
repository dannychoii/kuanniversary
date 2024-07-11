package kr.ac.korea.anniversary.global

import org.springframework.http.HttpStatus

data class CustomException(
    val httpStatus: HttpStatus,
    val msg: String,
) : RuntimeException(msg)
