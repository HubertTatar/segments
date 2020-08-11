plugins {
    kotlin("jvm") version "1.3.72"
}

group = "io.huta"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val vertxVersion = "3.9.2"
val arrowVersion = "0.10.5"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-config:$vertxVersion")

    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-syntax:$arrowVersion")
    implementation("io.arrow-kt:arrow-fx:$arrowVersion")

    testImplementation("io.rest-assured:rest-assured:4.2.0")
    testImplementation("org.hamcrest.java-hamcrest:2.0.0.0")
}
