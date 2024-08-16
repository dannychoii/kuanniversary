package kr.ac.korea.anniversary.controller.dto.request

data class AlumniCreateRequest(
    val name: String,
    val phoneNumber: String,
    val isMale: Boolean,
    val studentId: Int,
    val email: String,
    val company: String
)
