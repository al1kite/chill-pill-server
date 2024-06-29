package ddwu.com.mobile.finalreport.security

import ddwu.com.mobile.finalreport.constant.AuthConstants.Companion.ANONYMOUS_USER
import ddwu.com.mobile.finalreport.model.entity.maria.auth.User
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*


object LoginManager {

    val userDetails: User?
        get() {
            val authentication = authentication ?: return null
            return try {
                authentication.principal as User
            } catch (e: Exception) {
                null
            }
        }

    private val authentication: Authentication?
        get() {
            return try {
                SecurityContextHolder.getContext()?.authentication
            } catch (e: Exception) {
                null
            }
        }
}