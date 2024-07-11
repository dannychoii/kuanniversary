package kr.ac.korea.anniversary.service.dto.command

import kr.ac.korea.anniversary.global.CustomException
import org.springframework.http.HttpStatus

data class GuestBookPageCommand(
    val page: Long,
    val pageSize: Long,
    val isDesc: Boolean,
) {
    init {
        require(pageSize <= 100) { throw CustomException(HttpStatus.BAD_REQUEST, "$pageSize 는 100보다 작아야 한다.") }
    }
}
