/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/8.0.2/userguide/building_java_projects.html
 */

plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    java
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.danilopianini.gradle-java-qa") version "0.43.0"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.7.3")
    // Dipendenze di implementazione (main)
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.json:json:20220924")
    implementation("org.yaml:snakeyaml:2.2")

    // Dipendenze di test
    implementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    implementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    implementation("org.junit.platform:junit-platform-launcher:1.10.2")
    testImplementation("org.mockito:mockito-core:5.12.0")

}

application {
    // Define the main class for the application.
    mainClass.set("tesi.unibo.App")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
