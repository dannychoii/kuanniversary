package kr.ac.korea.anniversary.repository.entity

import jakarta.persistence.*
import kr.ac.korea.anniversary.helper.TimeHelper

@Entity
@Table(name = "alumni")
class Alumni(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long?,
    @Column(name = "name")
    val name: String,
    @Column(name = "phone_number")
    val phoneNumber: String,
    @Column(name = "is_male")
    val isMale: Boolean,
    @Column(name = "student_id")
    val studentId: Int,
    @Column(name = "email")
    val email: String,
    @Column(name = "company")
    val company: String,
    @Column(name = "created_at")
    var createdAt: Long = TimeHelper.nowKstTimeStamp(),
)