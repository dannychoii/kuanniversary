package kr.ac.korea.anniversary.helper

import java.time.LocalDateTime.now
import java.time.ZoneOffset

object TimeHelper {
    fun nowKstTimeStamp(): Long = now().toEpochSecond(ZoneOffset.of("+09:00"))
}
