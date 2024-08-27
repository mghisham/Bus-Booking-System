package com.hm.busbookingsystem.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtRequestFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        var jwt = ""
        var userName: String? = null
        if (!authorizationHeader.isNullOrEmpty() && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7)
            userName = jwtUtil.getUserNameFromToken(token = jwt)
        }
        if (userName != null && SecurityContextHolder.getContext().authentication == null) {
            val user = userDetailsService.loadUserByUsername(userName)
            if (jwtUtil.isTokenValid(jwt,user)){
                val authenticationToken = UsernamePasswordAuthenticationToken(user, null, user.authorities).apply {
                    details = WebAuthenticationDetailsSource().buildDetails(request)
                }
                SecurityContextHolder.getContext().authentication = authenticationToken
            }
        }
        filterChain.doFilter(request, response)
    }
}