package org.jnjeaaaat.openmarket.lock.parser

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.reflect.MethodSignature
import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.lock.exception.LockException
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.stereotype.Component

@Component
class LockKeyParser {

    private val parser = SpelExpressionParser()

    fun parse(
        joinPoint: ProceedingJoinPoint,
        expression: String
    ): String {

        val signature =
            joinPoint.signature as MethodSignature

        val parameterNames =
            signature.parameterNames

        val args = joinPoint.args

        val context = StandardEvaluationContext()

        parameterNames.forEachIndexed { index, name ->
            context.setVariable(name, args[index])
        }

        return parser.parseExpression(expression)
            .getValue(context, String::class.java)
            ?: throw LockException(ErrorCode.INTERNAL_ERROR)
    }
}