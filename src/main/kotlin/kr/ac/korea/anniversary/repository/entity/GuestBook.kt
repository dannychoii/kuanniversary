package kr.ac.korea.anniversary.repository.entity

import jakarta.persistence.*
import kr.ac.korea.anniversary.helper.TimeHelper

@Entity
@Table(name = "guest_book")
class GuestBook(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long?,
    @Column(name = "head")
    var head: String?,
    @Column(name = "content")
    var content: String?,
    @Column(name = "writer")
    var writer: String?,
    @Column(name = "is_confirmed")
    var isConfirmed: Boolean = false,
    @Column(name = "created_at")
    var createdAt: Long = TimeHelper.nowKstTimeStamp(),
    @Column(name = "updated_at")
    var updatedAt: Long = TimeHelper.nowKstTimeStamp(),
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "guestBook")
    val comments: MutableList<Comment> = mutableListOf()
)
