package org.jnjeaaaat.openmarket

import org.jnjeaaaat.openmarket.member.dto.request.openopen
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)

    openopen()
}

