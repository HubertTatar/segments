import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
    id("org.jlleitschuh.gradle.ktlint") version "9.3.0"
    id("idea")
}

group = "io.huta"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val vertxVersion = "4.0.0-milestone5"
val arrowVersion = "0.10.5"
val logbackVersion = "1.2.3"
val slf4jVersion = "1.7.30"
val logbackJacksonVersion = "0.1.5"
val jacksonDatabindVersion = "2.11.2"
val hamcrestVersion = "2.0.0.0"
val junitRunnerVersion = "1.6.2"
val junitVersion = "5.6.2"
val restAssuredVersion = "4.2.0"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-config:$vertxVersion")

    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
    implementation("io.arrow-kt:arrow-fx:$arrowVersion")

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("ch.qos.logback:logback-core:$logbackVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("ch.qos.logback.contrib:logback-jackson:$logbackJacksonVersion")
    implementation("ch.qos.logback.contrib:logback-json-classic:$logbackJacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonDatabindVersion")

    testImplementation("org.junit.platform:junit-platform-runner:$junitRunnerVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("org.hamcrest:java-hamcrest:$hamcrestVersion")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.allWarningsAsErrors = true
}
sourceSets.create("integration")

kotlin {
    sourceSets {
        val main by getting {}
        val integration by getting {
            resources.srcDir(project.file("src/integration/resources"))
            kotlin.srcDir(project.file("src/integration/kotlin"))
            dependencies {
                implementation("org.junit.platform:junit-platform-runner:$junitRunnerVersion")
                implementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
                implementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
            }
        }
        integration.dependsOn(main)
    }
}

tasks.register("integration", Test::class.java) {
    description = "Runs integration tests."
    group = "verification"
    shouldRunAfter("test")
    testClassesDirs = sourceSets["integration"].output.classesDirs
    classpath = sourceSets["integration"].runtimeClasspath
}

configurations.create("integration")
    .extendsFrom(configurations["testImplementation"])
    .extendsFrom(configurations["testRuntime"])

tasks.named("check") {
    dependsOn("integration")
}
