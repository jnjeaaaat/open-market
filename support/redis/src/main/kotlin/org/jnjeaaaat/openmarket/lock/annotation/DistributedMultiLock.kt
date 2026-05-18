package org.jnjeaaaat.openmarket.lock.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedMultiLock(
    val keys: Array<String>,
    val waitTime: Long = 5,
    val leaseTime: Long = 3
)