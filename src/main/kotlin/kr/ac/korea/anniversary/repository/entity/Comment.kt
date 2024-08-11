package kr.ac.korea.anniversary.repository.entity

import jakarta.persistence.*
import kr.ac.korea.anniversary.helper.TimeHelper

@Entity
@Table(name = "comment")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long?,
    @Column(name = "content")
    var content: String,
    @Column(name = "writer")
    var writer: String,
    @Column(name = "is_confirmed")
    var isConfirmed: Boolean = false,
    @Column(name = "created_at")
    var createdAt: Long = TimeHelper.nowKstTimeStamp(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name  = "guest_book_id")
    var guestBook: GuestBook? = null
)