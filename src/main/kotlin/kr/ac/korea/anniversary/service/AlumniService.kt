package kr.ac.korea.anniversary.service

import kr.ac.korea.anniversary.controller.dto.request.AlumniCreateRequest
import kr.ac.korea.anniversary.repository.AlumniJpaRepository
import kr.ac.korea.anniversary.repository.entity.Alumni
import org.springframework.stereotype.Service

@Service
class AlumniService(private val repository: AlumniJpaRepository) {

    fun register(command: AlumniCreateRequest): Alumni {
        return repository.save(
            Alumni(
                null,
                command.name,
                command.phoneNumber,
                command.isMale,
                command.studentId,
                command.email,
                command.company,
            )
        )
    }

    fun countTotalRegistered(): Int {
        return repository.count().toInt()
    }

}