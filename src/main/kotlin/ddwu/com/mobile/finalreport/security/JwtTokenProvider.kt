package ddwu.com.mobile.finalreport.security

import ddwu.com.mobile.finalreport.constant.CommonConstants
import ddwu.com.mobile.finalreport.service.auth.AuthExecutor
import ddwu.com.mobile.finalreport.service.auth.AuthService
import ddwu.com.mobile.finalreport.util.DateUtil
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class JwtTokenProvider (private val authExecutor: AuthExecutor) {
    @Value("\${jwt.secret}")
    private val secretKey: String? = null
    private var key: Key? = null

    @PostConstruct
    private fun initKey() {
        val decodedKey = Base64.getEncoder().encode(secretKey!!.toByteArray())
        key = Keys.hmacShaKeyFor(decodedKey)
    }

    private fun createToken(claims: Claims, validTime: Long): String {
        val now = Date()
        val expireTime = Date(now.time + validTime)
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()
    }

    fun validateJwtTokenExpireTime(jwtToken: String?): Boolean {
        return if (jwtToken == null) {
            false
        } else try {
            val claims = parseClaimsFromJwtToken(jwtToken)
            !claims.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun createNewAccessToken(claims: Claims): String {
        return createToken(claims, DateUtil.ONE_DAY_IN_MILLISECONDS)
    }

    fun createAccessToken(claims: Claims): String {
        return createToken(claims, DateUtil.ONE_HOUR_IN_MILLISECONDS)
    }

    fun createRefreshToken(claims: Claims): String {
        return createToken(claims, DateUtil.ONE_MONTH_IN_MILLISECONDS)
    }

    fun getAuthentication(jwtToken: String): Authentication? {
        val userId = getUserId(jwtToken)
        val userInfo: UserDetails = authExecutor.loadUserByUsername(userId)

        return userInfo.let {
            UsernamePasswordAuthenticationToken(it, "", it.authorities ?: mutableSetOf<SimpleGrantedAuthority>())
        }
    }

    private fun parseClaimsFromJwtToken(jwtToken: String): Claims {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .body
    }

    fun getUserId(token: String): String {
        return parseClaimsFromJwtToken(token).subject
    }

    fun getEmail(token: String): String? {
        return parseClaimsFromJwtToken(token)[CommonConstants.EMAIL] as String?
    }
}

