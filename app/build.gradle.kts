plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("io.gitlab.arturbosch.detekt")
}

apply( from = "$rootDir/base-module.gradle")

android {
    namespace = ProjectConfig.appId

    defaultConfig {
        applicationId = ProjectConfig.appId
    }
}