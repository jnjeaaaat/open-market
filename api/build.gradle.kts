dependencies {
    api(project(":domain"))
    api(project(":support:exception"))
    api(project(":support:logging"))
    api(project(":support:redis"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    runtimeOnly("com.h2database:h2")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.1")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.9.1")

    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")

    testImplementation("io.mockk:mockk:1.13.13")
    testImplementation("com.ninja-squad:springmockk:4.0.2")

    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter:1.1.10")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-kotlin:1.1.10")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-kotest:1.1.10")

    testImplementation("org.testcontainers:junit-jupiter:1.20.1")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
}