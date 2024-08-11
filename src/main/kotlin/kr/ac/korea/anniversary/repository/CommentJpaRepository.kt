package kr.ac.korea.anniversary.repository

import kr.ac.korea.anniversary.repository.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

interface CommentJpaRepository : JpaRepository<Comment, Long>