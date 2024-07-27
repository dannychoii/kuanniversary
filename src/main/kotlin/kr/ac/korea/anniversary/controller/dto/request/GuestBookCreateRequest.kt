package kr.ac.korea.anniversary.controller.dto.request

data class GuestBookCreateRequest(
    val head: String,
    val content: String,
    val writer: String,
)
