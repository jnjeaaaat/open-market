plugins {
    kotlin("kapt")
}

dependencies {
    implementation(project(":support:exception"))
    implementation(project(":support:jpa"))
    implementation(project(":support:event"))
    implementation(project(":support:logging"))
    implementation(project(":support:redis"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

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