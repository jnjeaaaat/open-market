package org.jnjeaaaat.openmarket.config

import org.jnjeaaaat.openmarket.aop.TraceIdFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class FilterConfig {

    @Bean
    fun traceIdFilter(): FilterRegistrationBean<TraceIdFilter> {
        val bean = FilterRegistrationBean(TraceIdFilter())
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }

}