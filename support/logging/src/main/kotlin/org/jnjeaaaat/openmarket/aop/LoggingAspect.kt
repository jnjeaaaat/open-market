package org.jnjeaaaat.openmarket.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.jnjeaaaat.openmarket.util.logger
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {

    private val log = logger()

    @Around("execution(* org.jnjeaaaat.openmarket..controller..*(..))")
    fun logExecution(joinPoint: ProceedingJoinPoint): Any? {

        val start = System.currentTimeMillis()

        log.info { "[START] \n${joinPoint.signature}" }

        return try {
            val result = joinPoint.proceed()
            val time = System.currentTimeMillis() - start

            log.info { "[END] \n${joinPoint.signature} (${time}ms)" }

            result
        } catch (e: RuntimeException) {
            log.error(e) { "[ERROR] ${joinPoint.signature}" }
            throw e
        }
    }
}