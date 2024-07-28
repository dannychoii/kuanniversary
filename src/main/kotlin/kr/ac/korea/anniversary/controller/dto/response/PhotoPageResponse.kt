package kr.ac.korea.anniversary.controller.dto.response

import kr.ac.korea.anniversary.repository.entity.Photo

data class PhotoPageResponse(
    val photos: List<Photo>,
    val page: Long,
    val pageSize: Long,
    val totalCount: Long,
)
