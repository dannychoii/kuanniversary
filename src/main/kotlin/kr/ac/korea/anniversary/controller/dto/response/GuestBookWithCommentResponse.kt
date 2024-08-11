package kr.ac.korea.anniversary.controller.dto.response

import kr.ac.korea.anniversary.repository.entity.GuestBook

data class GuestBookWithCommentResponse(
    val id: Long,
    val head: String?,
    val content: String?,
    val writer: String?,
    val isConfirmed: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val comments: List<CommentResponse>
) {
    companion object {
        fun from(guestBook: GuestBook, filterConfirmedComment: Boolean = false) = GuestBookWithCommentResponse(
            id = guestBook.id!!,
            head = guestBook.head,
            content = guestBook.content,
            writer = guestBook.writer,
            isConfirmed = guestBook.isConfirmed,
            createdAt = guestBook.createdAt,
            updatedAt = guestBook.updatedAt,
            comments = guestBook.comments.let { comments ->
                if (filterConfirmedComment) comments.filter { it.isConfirmed }
                else comments
            }.map { CommentResponse.from(it) },
        )
    }
}