package kr.ac.korea.anniversary.config

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.ac.korea.anniversary.global.CustomException
import org.springframework.http.HttpStatus
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

class BasicAuthInterceptor : HandlerInterceptor {
    companion object {
        val escapeSpaceRegex = "\\s".toRegex()
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val authorization = request.getHeader("Authorization").split(escapeSpaceRegex)[1]
        val decoded = Base64.getDecoder().decode(authorization).toString(Charsets.UTF_8)
        if (decoded != AdminAuthInfo.credential) {
            throw CustomException(HttpStatus.UNAUTHORIZED, "인증정보가 부정확합니다")
        }
        return super.preHandle(request, response, handler)
    }
}