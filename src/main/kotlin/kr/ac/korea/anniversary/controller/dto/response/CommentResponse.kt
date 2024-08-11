package kr.ac.korea.anniversary.controller.dto.response

import kr.ac.korea.anniversary.repository.entity.Comment


data class CommentResponse(
    val id: Long,
    var content: String,
    var writer: String,
    var isConfirmed: Boolean,
    var createdAt: Long,
) {
    companion object {
        fun from(comment: Comment) = CommentResponse(
            id = comment.id!!,
            content = comment.content,
            writer = comment.writer,
            isConfirmed = comment.isConfirmed,
            createdAt = comment.createdAt,
        )
    }
}
