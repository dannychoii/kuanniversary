package kr.ac.korea.anniversary.service

import kr.ac.korea.anniversary.repository.CommentJpaRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CommentAdminService(
    private val repository: CommentJpaRepository
) {
    fun updateIsConfirmed(id:Long, isConfirmed:Boolean){
        val comment = repository.findById(id).getOrNull()
        if(comment!=null){
            comment.isConfirmed = isConfirmed
        }

    }
    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

}
