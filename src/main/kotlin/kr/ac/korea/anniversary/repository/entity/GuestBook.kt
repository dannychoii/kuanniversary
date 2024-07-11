package kr.ac.korea.anniversary.repository.entity

import java.time.LocalDateTime.now
import java.time.ZoneOffset

class GuestBook(
    val id: Long?,
    val head: String?,
    val content: String?,
    val isVisible: Boolean = false,
    val createdAt: Long = now().toEpochSecond(ZoneOffset.of("KST")),
    val updatedAt: Long = now().toEpochSecond(ZoneOffset.of("KST")),
)
