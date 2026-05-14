package org.jnjeaaaat.openmarket.member.event

import org.jnjeaaaat.openmarket.common.DomainEvent

sealed interface MemberEvent : DomainEvent

data class MemberRegisteredEvent(val memberId: Long) : MemberEvent
data class MemberDeletedEvent(val memberId: Long) : MemberEvent