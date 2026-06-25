package org.jnjeaaaat.openmarket.lock.aspect

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class AopForTransaction {

    // 부모 트랜잭션과 별개로 무조건 새 트랜잭션을 수립
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun <T> proceed(action: () -> T): T {
        return action()
    }
}