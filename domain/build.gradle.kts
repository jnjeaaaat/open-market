plugins {
    kotlin("kapt")
}

dependencies {
    api(project(":support:exception"))
    api(project(":support:jpa"))
    api(project(":support:event"))
    api(project(":support:logging"))
    api(project(":support:redis"))
    api(project(":support:rabbitmq"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // cache
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("org.springframework.boot:spring-boot-starter-cache")

    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    // rabbitmq
    implementation ("org.springframework.boot:spring-boot-starter-amqp")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")

    kapt("com.querydsl:querydsl-apt:5.1.0:jakarta")
    kapt("jakarta.annotation:jakarta.annotation-api")
    kapt("jakarta.persistence:jakarta.persistence-api")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}