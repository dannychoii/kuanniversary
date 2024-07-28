package kr.ac.korea.anniversary.controller

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kr.ac.korea.anniversary.controller.dto.request.GuestBookCreateRequest
import kr.ac.korea.anniversary.controller.dto.request.GuestBookUpdateRequest
import kr.ac.korea.anniversary.controller.dto.response.GuestBookPageResponse
import kr.ac.korea.anniversary.global.PageCommand
import kr.ac.korea.anniversary.repository.entity.GuestBook
import kr.ac.korea.anniversary.service.GuestBookService
import kr.ac.korea.anniversary.service.dto.command.GuestBookSearchCommand
import org.springframework.web.bind.annotation.*

@Tag(name = "방명록")
@RestController
class GuestBookController(
    val service: GuestBookService,
) {
    @GetMapping("api/v1/guest-book/{id}")
    fun getGuestBook(
        @PathVariable id: Long,
    ): GuestBook? {
        return service.findById(id)
    }

    @GetMapping("api/v1/guest-book")
    fun getGuestBook(
        @Parameter(description = "승인받은 방명록 글만 볼 것인지, default = true", example = "1720618920")
        @RequestParam isConfirmed: Boolean?,
        @Parameter(description = "초 단위의 timestamp", example = "1720618920")
        @RequestParam fromTs: Long?,
        @Parameter(description = "초 단위의 timestamp", example = "1720618920")
        @RequestParam toTs: Long?,
        @Parameter(description = "default=0", example = "0")
        @RequestParam page: Long?,
        @Parameter(description = "default=20, max=100, 한번에 조회할 크기", example = "20")
        @RequestParam pageSize: Long?,
        @Parameter(description = "default=true")
        @RequestParam isDesc: Boolean?,
    ): GuestBookPageResponse {
        val (elements, totalCount) =
            service.search(
                GuestBookSearchCommand(isConfirmed ?: true, fromTs, toTs),
                PageCommand(page ?: 0, pageSize ?: 20, isDesc ?: true),
            )
        return GuestBookPageResponse(
            guestBooks = elements,
            page = page!!,
            pageSize = pageSize!!,
            totalCount = totalCount,
        )
    }

    @PatchMapping("api/v1/guest-book/{id}")
    fun updateGuestBookVisibility(
        @PathVariable id: Long,
        @RequestBody request: GuestBookUpdateRequest,
    ): GuestBook? {
        return service.updateConfirm(id, request.isConfirmed)
    }

    @PostMapping("api/v1/guest-book")
    fun createGuestBook(
        @RequestBody request: GuestBookCreateRequest,
    ): GuestBook {
        return service.create(
            GuestBook(
                id = null,
                head = request.head,
                content = request.content,
                writer = request.writer,
            ),
        )
    }

    @DeleteMapping("api/v1/guest-book/{id}")
    fun deleteGuestBook(
        @PathVariable id: Long,
    ) {
        return service.deleteById(id)
    }
}
