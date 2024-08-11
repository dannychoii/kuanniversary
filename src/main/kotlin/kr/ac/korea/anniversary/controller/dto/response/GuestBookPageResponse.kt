package kr.ac.korea.anniversary.controller.dto.response


data class GuestBookPageResponse(
    val guestBooks: List<GuestBookResponse>,
    val page: Long,
    val pageSize: Long,
    val totalCount: Long,
)
