package kr.ac.korea.anniversary.service

import kr.ac.korea.anniversary.global.PageCommand
import kr.ac.korea.anniversary.repository.CommentJpaRepository
import kr.ac.korea.anniversary.repository.GuestBookJpaRepository
import kr.ac.korea.anniversary.repository.GuestBookRepository
import kr.ac.korea.anniversary.repository.entity.Comment
import kr.ac.korea.anniversary.repository.entity.GuestBook
import kr.ac.korea.anniversary.service.dto.command.GuestBookSearchCommand
import org.springframework.stereotype.Service

@Service
class GuestBookService(
    val repository: GuestBookRepository,
    val jpaRepository: GuestBookJpaRepository,
    val commentJpaRepository: CommentJpaRepository
) {
    fun findByIdAndIsConfirmed(id: Long, isConfirmed: Boolean): GuestBook? {
        return jpaRepository.find(id, isConfirmed)
    }

    fun search(
        command: GuestBookSearchCommand,
        pageCommand: PageCommand,
    ): Pair<List<GuestBook>, Long> {
        return repository.search(command, pageCommand)
    }

    fun create(guestBook: GuestBook): GuestBook {
        return repository.insert(guestBook)
    }

    fun addComment(guestBookId: Long, content: String, writer: String) {
        val guestbook = repository.findById(guestBookId) ?: return
        val comment = Comment(
            id = null,
            content = content,
            writer = writer,
            guestBook = guestbook
        )
        commentJpaRepository.save(comment)
    }

}
