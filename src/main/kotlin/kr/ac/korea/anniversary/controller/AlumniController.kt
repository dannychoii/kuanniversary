package kr.ac.korea.anniversary.controller

import kr.ac.korea.anniversary.controller.dto.request.AlumniCreateRequest
import kr.ac.korea.anniversary.repository.entity.Alumni
import kr.ac.korea.anniversary.service.AlumniService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AlumniController(
    private val service : AlumniService
) {

    @PostMapping("api/v1/alumni")
    fun registerAlumni(
        @RequestBody request: AlumniCreateRequest
    ):Alumni{
        return service.register(request)
    }

    @GetMapping("api/v1/alumni")
    fun countTotalRegistered():Int{
        return service.countTotalRegistered()
    }
}