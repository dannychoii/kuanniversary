package kr.ac.korea.anniversary.controller.dto.response

import kr.ac.korea.anniversary.repository.entity.Photo

data class PhotoResponse(
    val id: Long,
    val imageUrl: String,
) {
    companion object {
        fun from(photo: Photo): PhotoResponse {
            return PhotoResponse(
                photo.id,
                photo.imageUrl,
            )
        }
    }
}
