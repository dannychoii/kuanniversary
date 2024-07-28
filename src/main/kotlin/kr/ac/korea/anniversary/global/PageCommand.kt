package kr.ac.korea.anniversary.global

import org.springframework.http.HttpStatus

data class PageCommand(
    val page: Long,
    val pageSize: Long,
    val isDesc: Boolean,
) {
    init {
        require(pageSize <= 100) { throw CustomException(HttpStatus.BAD_REQUEST, "$pageSize 는 100보다 작아야 한다.") }
    }
}
