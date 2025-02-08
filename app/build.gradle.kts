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

dependencies {
    implementation(project(":common"))
    implementation(project(":features:home"))
    implementation(project(":features:search"))
    implementation(project(":features:favourite"))
    implementation(project(":features:settings"))
}