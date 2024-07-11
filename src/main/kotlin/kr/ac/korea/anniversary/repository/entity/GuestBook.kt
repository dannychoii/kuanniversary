package kr.ac.korea.anniversary.repository.entity

import kr.ac.korea.anniversary.helper.TimeHelper

class GuestBook(
    val id: Long?,
    val head: String?,
    val content: String?,
    val isConfirmed: Boolean = false,
    val createdAt: Long = TimeHelper.nowKstTimeStamp(),
    val updatedAt: Long = TimeHelper.nowKstTimeStamp(),
)
