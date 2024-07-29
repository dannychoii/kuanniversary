package kr.ac.korea.anniversary.service

import kr.ac.korea.anniversary.global.PageCommand
import kr.ac.korea.anniversary.repository.GuestBookRepository
import kr.ac.korea.anniversary.repository.entity.GuestBook
import kr.ac.korea.anniversary.service.dto.command.GuestBookSearchCommand
import org.springframework.stereotype.Service

@Service
class GuestBookService(
    val repository: GuestBookRepository,
) {
    fun findById(id: Long): GuestBook? {
        return repository.findById(id)
    }

    fun findByIdAndIsConfirmed(id: Long, isConfirmed: Boolean): GuestBook? {
        return repository.findByIdAndIsConfirmed(id, isConfirmed)
    }

    fun updateConfirm(
        id: Long,
        isConfirmed: Boolean,
    ): GuestBook? {
        repository.updateConfirm(id, isConfirmed)
        return repository.findById(id)
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

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }
}
