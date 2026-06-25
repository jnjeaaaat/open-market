dependencies {
    implementation(project(":support:exception"))
    implementation(project(":support:logging"))

    api("org.redisson:redisson-spring-boot-starter:3.32.0")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}