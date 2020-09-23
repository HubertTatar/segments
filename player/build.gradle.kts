/*
    `val integration by getting` was not correctly registering sourceSet,
     gradle was not detecting `integration` sourceSet - thus `sourceSets.create("integration")`
     sourceSets.create("integration") create SourceSet and it is not able to configure kotlin
     `val integration by getting` create KotlinSourceSet and allows to configure Kotlin
 */

val sourceSets = extensions.getByName("sourceSets") as SourceSetContainer
sourceSets.create("integration")

val mainJ = sourceSets["main"].output

val kotlinExt = extensions.getByName("kotlin") as org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

val junitVersion = "5.6.2"

kotlinExt.sourceSets {
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
tasks.named<Test>("integration") {
    useJUnitPlatform()
}

/*
    without it gradle doesn't detect any @Test methods
*/
tasks.named<Test>("test") {
    useJUnitPlatform()
}