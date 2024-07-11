package kr.ac.korea.anniversary.service.dto.command

import kr.ac.korea.anniversary.global.CustomException
import org.springframework.http.HttpStatus

data class GuestBookSearchCommand(
    val fromTs: Long?,
    val toTs: Long?,
) {
    init {
        if (fromTs != null && toTs != null) {
            require(fromTs < toTs) { throw CustomException(HttpStatus.BAD_REQUEST, "fromTs < toTs 여야 한다") }
        }
    }
}
