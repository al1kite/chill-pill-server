package ddwu.com.mobile.finalreport.repository.maria.auth

import ddwu.com.mobile.finalreport.model.entity.maria.auth.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findUserInfoByEmail(email: String): User?
}