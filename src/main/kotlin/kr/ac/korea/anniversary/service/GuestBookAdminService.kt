package kr.ac.korea.anniversary.service

import kr.ac.korea.anniversary.global.PageCommand
import kr.ac.korea.anniversary.repository.GuestBookJpaRepository
import kr.ac.korea.anniversary.repository.GuestBookRepository
import kr.ac.korea.anniversary.repository.entity.GuestBook
import kr.ac.korea.anniversary.service.dto.command.GuestBookSearchCommand
import org.springframework.stereotype.Service


@Service
class GuestBookAdminService(
    private val nativeRepository: GuestBookRepository,
    private val repository: GuestBookJpaRepository
) {
    fun findById(id: Long): GuestBook? {
        return repository.findById(id)
    }

    fun search(
        command: GuestBookSearchCommand,
        pageCommand: PageCommand,
    ): Pair<List<GuestBook>, Long> {
        return nativeRepository.search(command, pageCommand)
    }

    fun updateConfirm(
        id: Long,
        isConfirmed: Boolean,
    ): GuestBook? {
        val guestBook = repository.findById(id) ?: return null
        guestBook.isConfirmed = isConfirmed
        return repository.save(guestBook)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }
}