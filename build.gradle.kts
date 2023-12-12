import com.adarshr.gradle.testlogger.theme.ThemeType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // id("java")
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    //id("io.kotest") version "0.4.10"
    id("com.adarshr.test-logger") version "4.0.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

val arrowVersion        = "1.2.1" // https://github.com/arrow-kt/arrow
val kotestVersion       = "5.8.0"   // https://github.com/kotest/kotest
val mockkVersion        = "1.13.8"  // https://github.com/mockk/mockk

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springModulithVersion"] = "1.1.0"
extra["springCloudVersion"] = "2023.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.3.0")
    implementation("io.micrometer:micrometer-core")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("io.micrometer:micrometer-tracing-bridge-otel")
    implementation("io.arrow-kt:arrow-core:1.2.1")
    implementation("io.arrow-kt:arrow-fx-coroutines:1.2.1")
    //implementation("io.opentelemetry:opentelemetry-exporter-otlp")

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
    runtimeOnly("org.springframework.modulith:spring-modulith-observability")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    implementation("io.github.oshai:kotlin-logging-jvm:5.1.1")

    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
    //testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")   // https://github.com/kotest/kotest-extensions-spring
    testImplementation("io.mockk:mockk:${mockkVersion}")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
//    testLogging {
//        showCauses = true
//        showExceptions = true
//        showStackTraces = true
////        showStandardStreams = true
//        showCauses = true
////        showExceptions = true
////        showStackTraces = true
//////        // exceptionFormat = "full"
//          events("passed", "skipped", "failed")
//
//    }
}

testlogger {
    // pick a theme - mocha, standard, plain, mocha-parallel, standard-parallel or plain-parallel
    theme = ThemeType.MOCHA

    // set to false to disable detailed failure logs
    showExceptions = true

    // set to false to hide stack traces
    showStackTraces = true

    // set to true to remove any filtering applied to stack traces
    showFullStackTraces = false

    // set to false to hide exception causes
    showCauses = true

    // set threshold in milliseconds to highlight slow tests
    slowThreshold = 2000

    // displays a breakdown of passes, failures and skips along with total duration
    showSummary = true

    // set to true to see simple class names
    showSimpleNames = false

    // set to false to hide passed tests
    showPassed = true

    // set to false to hide skipped tests
    showSkipped = true

    // set to false to hide failed tests
    showFailed = true

    // enable to see standard out and error streams inline with the test results
    showStandardStreams = false

    // set to false to hide passed standard out and error streams
    showPassedStandardStreams = true

    // set to false to hide skipped standard out and error streams
    showSkippedStandardStreams = true

    // set to false to hide failed standard out and error streams
    showFailedStandardStreams = true

    logLevel = LogLevel.LIFECYCLE
}
