package org.jnjeaaaat.openmarket.util

import org.slf4j.MDC

object MDCUtil {
    fun put(key: String, value: String) {
        MDC.put(key, value)
    }

    fun clear() {
        MDC.clear()
    }

    inline fun <T> withMdc(key: String, value: String?, block: () -> T): T {
        if (value != null) MDC.put(key, value)
        return try {
            block()
        } finally {
            if (value != null) MDC.remove(key)
        }
    }
}