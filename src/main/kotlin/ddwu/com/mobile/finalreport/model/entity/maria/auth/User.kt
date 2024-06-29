package ddwu.com.mobile.finalreport.model.entity.maria.auth

import jakarta.persistence.*

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "USER")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userSeq: Int = 0,

    @Column(nullable = false)
    var email: String? = null,

    @Column(nullable = false)
    var pw: String? = null,

    @Transient
    var authorities: MutableSet<SimpleGrantedAuthority>? = mutableSetOf()

) : UserDetails {

    override fun getUsername(): String {
        return this.email ?: ""
    }

    override fun getPassword(): String {
        return this.pw ?: ""
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities ?: emptySet()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}