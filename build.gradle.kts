import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("org.jlleitschuh.gradle.ktlint") version "9.3.0"
    id("idea")
}

idea {
    module {
        isDownloadSources = true
    }
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

allprojects {
    group = "io.huta"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {

    plugins.apply("org.jetbrains.kotlin.jvm")
    plugins.apply("idea")

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
}

/*
    Linter.
    Disable import ordering rule, IDEA is enough
*/
ktlint {
    disabledRules.set(setOf("import-ordering"))
}
