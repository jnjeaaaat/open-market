package org.jnjeaaaat.openmarket.common

import org.springframework.context.ApplicationEventPublisher

fun ApplicationEventPublisher.publish(event: DomainEvent) {
    this.publishEvent(event)
}