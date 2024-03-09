package com.albatros.springsecurity.filter

import com.albatros.springsecurity.domain.service.JwtService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {
    companion object {
        const val BEARER_PREFIX = "Bearer "
        const val HEADER_NAME = "Authorization"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val authHeader = request.getHeader(HEADER_NAME)
        if (authHeader.isNullOrEmpty() || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substringAfter(BEARER_PREFIX)
        val username =
            try {
                jwtService.extractUsername(jwt)
            } catch (e: ExpiredJwtException) {
                filterChain.doFilter(request, response)
                return
            } catch (e: MalformedJwtException) {
                filterChain.doFilter(request, response)
                return
            }

        username?.let {
            if (it.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userDetailsService.loadUserByUsername(username)
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    val context = SecurityContextHolder.createEmptyContext()
                    val authToken =
                        UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.authorities,
                        )
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    context.authentication = authToken
                    SecurityContextHolder.setContext(context)
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}
