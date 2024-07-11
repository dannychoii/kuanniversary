package kr.ac.korea.anniversary.controller.dto.response

import kr.ac.korea.anniversary.repository.entity.GuestBook

data class GuestBookPageResponse(
    val guestBooks: List<GuestBook>,
    val page: Long,
    val pageSize: Long,
    val totalCount: Long,
)
