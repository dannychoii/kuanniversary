package kr.ac.korea.anniversary.controller.dto.request

data class GuestBookCommentAddRequest(
    val guestBookId: Long,
    val content: String,
    val writer: String?,
)
