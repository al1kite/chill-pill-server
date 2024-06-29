package ddwu.com.mobile.finalreport.service.auth

import ddwu.com.mobile.finalreport.constant.AuthConstants
import ddwu.com.mobile.finalreport.constant.CommonConstants
import ddwu.com.mobile.finalreport.model.entity.maria.auth.User
import ddwu.com.mobile.finalreport.model.request.auth.LoginRequest
import ddwu.com.mobile.finalreport.model.response.auth.TokenResponse
import ddwu.com.mobile.finalreport.repository.maria.auth.UserRepository
import ddwu.com.mobile.finalreport.security.JwtTokenProvider
import ddwu.com.mobile.finalreport.util.DateUtil
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthService @Autowired constructor(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
)  {

    @Transactional
    fun login(response: HttpServletResponse, request: LoginRequest): TokenResponse {
        val userInfo = userRepository.findUserInfoByEmail(request.email)

        val user = if (userInfo == null) {
            userRepository.save(User(
                email = request.email,
                pw = request.password
            ))
        } else {
            if (userInfo.pw != request.password)
                throw RuntimeException("Password not matched")
            userInfo
        }

        return makeToken(response, user)
    }

    private fun makeToken(response: HttpServletResponse, userInfo: User): TokenResponse {
        val claims: Claims = Jwts.claims().setSubject(userInfo.email)
        claims[CommonConstants.EMAIL] = userInfo.email

        val accessToken: String = jwtTokenProvider.createAccessToken(claims)
        val refreshToken: String = jwtTokenProvider.createRefreshToken(claims)

        val cookie = jakarta.servlet.http.Cookie(AuthConstants.REFRESH_TOKEN_STR, refreshToken)
        cookie.maxAge = DateUtil.ONE_MONTH_IN_MILLISECONDS.toInt()
        response.addCookie(cookie)

        return TokenResponse(accessToken = accessToken)
    }
}
