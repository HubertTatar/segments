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
val junitVersion = "5.6.2"
val restAssuredVersion = "4.2.0"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-config:$vertxVersion")
    implementation("io.vertx:vertx-unit:$vertxVersion")
    implementation("io.vertx:vertx-junit5:$vertxVersion")
    implementation("io.vertx:vertx-web-client:$vertxVersion")
//    implementation("io.vertx:vertx-junit5-web-client:4.0.0-milestone4")

    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
    implementation("io.arrow-kt:arrow-fx:$arrowVersion")

    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("ch.qos.logback:logback-core:$logbackVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("ch.qos.logback.contrib:logback-jackson:$logbackJacksonVersion")
    implementation("ch.qos.logback.contrib:logback-json-classic:$logbackJacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonDatabindVersion")

    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("org.hamcrest:java-hamcrest:$hamcrestVersion")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.allWarningsAsErrors = true
}

/*
    `val integration by getting` was not correctly registering sourceSet,
     gradle was not detecting `integration` sourceSet - thus `sourceSets.create("integration")`
     sourceSets.create("integration") create SourceSet and it is not able to configure kotlin
     `val integration by getting` create KotlinSourceSet and allows to configure Kotlin
 */
sourceSets.create("integration")

val mainJ = sourceSets["main"].output

kotlin {
    sourceSets {
        val main by getting {}
        val integration by getting {
            resources.srcDir(project.file("src/integration/resources"))
            kotlin.srcDir(project.file("src/integration/kotlin"))
            dependencies {
                implementation("org.junit.jupiter:junit-jupiter:$junitVersion")
                implementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
                implementation(mainJ)
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

/*
    without it gradle doesn't detect any @Test methods
 */
tasks.named<Test>("test") {
    useJUnitPlatform()
}

/*
    without it gradle doesn't detect any @Test methods
 */
tasks.named<Test>("integration") {
    useJUnitPlatform()
}

/*
    Disable import ordering rule, IDEA is enough
 */
ktlint {
    disabledRules.set(setOf("import-ordering"))
}
