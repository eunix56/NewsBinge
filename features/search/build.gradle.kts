plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "com.app.search"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerVersion = "1.8.10"
        kotlinCompilerExtensionVersion = "1.4.2"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    val compose_ui_version : String by rootProject.extra

    implementation(project(":common"))
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.compose.ui:ui:$compose_ui_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.squareup.retrofit2:retrofit:2.8.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.10.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.6.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    testImplementation("junit:junit:4.13.2")
    debugImplementation ("androidx.compose.ui:ui-tooling:$compose_ui_version")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}