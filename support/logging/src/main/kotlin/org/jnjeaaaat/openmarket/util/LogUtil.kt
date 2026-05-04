package org.jnjeaaaat.openmarket.util

import io.github.oshai.kotlinlogging.KotlinLogging

inline fun <reified T> T.logger() =
    KotlinLogging.logger(T::class.java.name)