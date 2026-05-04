package org.jnjeaaaat.openmarket.aop

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

class TraceIdFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val traceId = UUID.randomUUID().toString().substring(0, 8)
        val uri = request.requestURI

        MDC.put("traceId", traceId)
        MDC.put("uri", uri)

        try {
            filterChain.doFilter(request, response)
        } finally {
            MDC.clear()
        }
    }

}