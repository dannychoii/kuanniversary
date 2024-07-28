package kr.ac.korea.anniversary.service

import kr.ac.korea.anniversary.global.PageCommand
import kr.ac.korea.anniversary.repository.PhotoRepository
import kr.ac.korea.anniversary.repository.entity.Photo
import org.springframework.stereotype.Service

@Service
class PhotoService(
    private val repository: PhotoRepository,
) {
    fun findById(id: Long): Photo? {
        return repository.findById(id)
    }

    fun search(pageCommand: PageCommand): Pair<List<Photo>, Long> {
        return repository.search(pageCommand)
    }
}
