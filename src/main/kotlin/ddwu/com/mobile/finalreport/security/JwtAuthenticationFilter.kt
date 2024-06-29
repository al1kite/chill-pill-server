package ddwu.com.mobile.finalreport.security

import ddwu.com.mobile.finalreport.constant.AuthConstants
import ddwu.com.mobile.finalreport.constant.CommonConstants
import ddwu.com.mobile.finalreport.service.auth.AuthService
import io.jsonwebtoken.Jwts
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


class JwtAuthenticationFilter(private val jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            var accessToken: String? = ""
            if (request.getHeader(AuthConstants.AUTHORIZATION) != null)
                accessToken = request.getHeader(AuthConstants.AUTHORIZATION).replace(AuthConstants.BEARER, "")

            if (jwtTokenProvider.validateJwtTokenExpireTime(accessToken)) {
                setAuthentication(accessToken)
            } else {
                val refreshToken = request.getHeader(AuthConstants.REFRESH_TOKEN_STR)
                if (jwtTokenProvider.validateJwtTokenExpireTime(refreshToken)) {
                    val newAccessToken = createNewAccessToken(refreshToken)
                    response.setHeader(AuthConstants.ACCESS_TOKEN_STR, accessToken)
                    setAuthentication(newAccessToken)
                }
            }
        } catch (e: Exception) {
           throw RuntimeException("Token Validation Failed")
        }
        filterChain.doFilter(request, response)
    }

    private fun createNewAccessToken(refreshToken: String): String {
        val userId = jwtTokenProvider.getUserId(refreshToken)
        val email = jwtTokenProvider.getEmail(refreshToken)
        val claims = Jwts.claims().setSubject(userId)
        claims[CommonConstants.EMAIL] = email
        return jwtTokenProvider.createNewAccessToken(claims)
    }

    private fun setAuthentication(accessToken: String?) {
        val authentication = jwtTokenProvider.getAuthentication(accessToken!!)
        SecurityContextHolder.getContext().authentication = authentication
    }
}
