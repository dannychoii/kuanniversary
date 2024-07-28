package kr.ac.korea.anniversary.controller

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kr.ac.korea.anniversary.controller.dto.response.PhotoPageResponse
import kr.ac.korea.anniversary.controller.dto.response.PhotoResponse
import kr.ac.korea.anniversary.global.PageCommand
import kr.ac.korea.anniversary.service.PhotoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "이미지")
@RestController
class PhotoController(
    val service: PhotoService,
) {
    @GetMapping("api/v1/photo/{id}")
    fun getImage(
        @PathVariable id: Long,
    ): PhotoResponse? {
        return service.findById(id)?.let {
            PhotoResponse.from(it)
        }
    }

    @GetMapping("api/v1/photo")
    fun searchImage(
        @Parameter(description = "default=0", example = "0")
        @RequestParam page: Long?,
        @Parameter(description = "default=20, max=100, 한번에 조회할 크기", example = "20")
        @RequestParam pageSize: Long?,
    ): PhotoPageResponse {
        val (images, count) = service.search(PageCommand(page ?: 0, pageSize ?: 20, true))
        return PhotoPageResponse(
            photos = images,
            page = page!!,
            pageSize = pageSize!!,
            totalCount = count,
        )
    }
}
