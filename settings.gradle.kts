pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.polyfrost.cc/releases")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "PolySigning"

include(":cli")
