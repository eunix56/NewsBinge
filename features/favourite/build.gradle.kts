plugins {
    `android-library`
    `kotlin-android`
    id("io.gitlab.arturbosch.detekt")
}

apply( from = "$rootDir/base-module.gradle")

android {
    namespace = "com.app.favourite"
}