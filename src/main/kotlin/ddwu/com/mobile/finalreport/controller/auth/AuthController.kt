package ddwu.com.mobile.finalreport.controller.auth

import ddwu.com.mobile.finalreport.model.request.auth.LoginRequest
import ddwu.com.mobile.finalreport.model.response.auth.TokenResponse
import ddwu.com.mobile.finalreport.service.auth.AuthService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Validated
@RestController
@RequestMapping(value = ["/api/auth"])
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun doLogin(response: HttpServletResponse, @RequestBody loginRequest: LoginRequest): ResponseEntity<TokenResponse> {
        return ResponseEntity.ok(authService.login(response, loginRequest))
    }
}
