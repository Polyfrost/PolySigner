plugins {
    kotlin("jvm") version "1.9.0"
    application
}

repositories {
    mavenCentral()
}

group = "org.polyfrost.signing.cli"
version = "0.1.0"

application {
    mainClass.set("org.polyfrost.signing.cli.MainKt")
}

kotlin {
    jvmToolchain(8)
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
}