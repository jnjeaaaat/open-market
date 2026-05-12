dependencies {
    api("io.github.oshai:kotlin-logging-jvm:5.1.0")
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}