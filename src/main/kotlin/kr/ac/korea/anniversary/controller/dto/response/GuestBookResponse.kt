package kr.ac.korea.anniversary.controller.dto.response

import kr.ac.korea.anniversary.repository.entity.GuestBook

data class GuestBookResponse(
    val id: Long,
    val head: String?,
    val content: String?,
    val writer: String?,
    val isConfirmed: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
) {
    companion object {
        fun from(guestBook: GuestBook) = GuestBookResponse(
            guestBook.id!!,
            guestBook.head,
            guestBook.content,
            guestBook.writer,
            guestBook.isConfirmed,
            guestBook.createdAt,
            guestBook.updatedAt,
        )
    }
}