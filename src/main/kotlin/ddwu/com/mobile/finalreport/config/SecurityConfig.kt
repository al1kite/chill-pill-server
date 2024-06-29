package ddwu.com.mobile.finalreport.config

import ddwu.com.mobile.finalreport.security.JwtAuthenticationFilter
import ddwu.com.mobile.finalreport.security.JwtTokenProvider
import org.mariadb.jdbc.internal.logging.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig (private val jwtTokenProvider: JwtTokenProvider){

    private val logger = LoggerFactory.getLogger(SecurityConfig::class.java)

    @Bean
    @Order(1)
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        logger.info("Configuring webSecurityCustomizer")
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers(
                    "/api/auth/**", "/swagger-ui/**", "/swagger/**", "/swagger-resources/**", "/swagger-ui.html",
                    "/v3/api-docs/**", "/css/**", "/js/**", "/img/**", "/lib/**",
                    "/configuration/ui", "/configuration/security", "/webjars/**", "/api/auth/**"
                )
        }
    }

    @Bean
    @Order(2)
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .cors { cors -> cors.disable() }
            .sessionManagement { sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/**").permitAll()
                    .anyRequest().authenticated()
            } .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }
}