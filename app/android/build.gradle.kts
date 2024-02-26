plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose)
    alias(libs.plugins.test.mokkery)
}

android {
    namespace = "dev.bapps.homevue.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.bapps.homevue.android"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":core:logger"))
    implementation(project(":core:design"))
    implementation(projects.app.shared)
    implementation(libs.androidx.activity.compose)
    implementation(compose.ui)
    implementation(compose.material3)
    implementation(compose.preview)
    debugImplementation(compose.uiTooling)
}