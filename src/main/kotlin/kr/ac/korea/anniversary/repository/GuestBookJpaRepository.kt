package kr.ac.korea.anniversary.repository

import kr.ac.korea.anniversary.repository.entity.GuestBook
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository

interface GuestBookJpaRepository : Repository<GuestBook, Long> {

    fun findById(id: Long): GuestBook?

    @Query(value = "select distinct g from GuestBook g left join fetch g.comments WHERE g.id = ?1 AND g.isConfirmed = ?2")
    fun find(id: Long, isConfirmed: Boolean): GuestBook?

    fun save(guestBook: GuestBook): GuestBook
    fun deleteById(id: Long)


}