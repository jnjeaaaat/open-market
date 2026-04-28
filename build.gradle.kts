plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21" apply false
    kotlin("plugin.jpa") version "2.2.21" apply false

    id("org.springframework.boot") version "4.0.6" apply false
    id("io.spring.dependency-management") version "1.1.7" apply false
}

allprojects {
    group = "org.jnjeaaaat"
    version = "0.0.1-SNAPSHOT"
    description = "open-market"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")

        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webmvc")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("tools.jackson.module:jackson-module-kotlin")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")

        runtimeOnly("com.h2database:h2")
        runtimeOnly("com.mysql:mysql-connector-j")

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testCompileOnly("org.projectlombok:lombok")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
        testAnnotationProcessor("org.projectlombok:lombok")
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-Xjsr305=strict",
                "-Xannotation-default-target=param-property"
            )
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}