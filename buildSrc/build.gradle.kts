plugins {
    `kotlin-dsl`
    id("io.gitlab.arturbosch.detekt") version "1.18.1" apply false
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.10")
    implementation("com.android.tools.build:gradle:8.0.2")
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "21"
}