package kr.ac.korea.anniversary.controller

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kr.ac.korea.anniversary.controller.dto.request.GuestBookCreateRequest
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
        return service.findByIdAndIsConfirmed(id, isConfirmed = true)
    }

    @GetMapping("api/v1/guest-book")
    fun getGuestBook(
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
                GuestBookSearchCommand(true, fromTs, toTs),
                PageCommand(page ?: 0, pageSize ?: 20, isDesc ?: true),
            )
        return GuestBookPageResponse(
            guestBooks = elements,
            page = page ?: 0,
            pageSize = pageSize ?: 20,
            totalCount = totalCount,
        )
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
}
