plugins {
    kotlin("kapt")
}

dependencies {
    // queryDsl
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