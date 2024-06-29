package ddwu.com.mobile.finalreport.service.auth

import ddwu.com.mobile.finalreport.model.entity.maria.auth.User
import ddwu.com.mobile.finalreport.repository.maria.auth.UserRepository
import ddwu.com.mobile.finalreport.security.JwtTokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthExecutor @Autowired constructor(
private val userRepository: UserRepository) : UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userId: String?): UserDetails {
        if (userId.isNullOrBlank()) {
            throw UsernameNotFoundException("User ID must not be null or blank")
        }

        val userInfo: User = userRepository.findUserInfoByEmail(userId)
            ?: throw UsernameNotFoundException("User not found with ID: $userId")

        userInfo.authorities = mutableSetOf(SimpleGrantedAuthority("ROLE_USER"))

        return userInfo
    }
}