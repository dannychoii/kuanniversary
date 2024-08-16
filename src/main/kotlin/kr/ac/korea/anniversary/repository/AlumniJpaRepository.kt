package kr.ac.korea.anniversary.repository

import kr.ac.korea.anniversary.repository.entity.Alumni
import org.springframework.data.jpa.repository.JpaRepository

interface AlumniJpaRepository : JpaRepository<Alumni, Long>