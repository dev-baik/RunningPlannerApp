import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "com.android.master.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "KAKAO_NATIVE_API_KEY", properties["kakao.navtive.app.key"].toString())
        buildConfigField("String", "NAVER_CLIENT_ID", properties["NAVER_CLIENT_ID"].toString())
        buildConfigField("String", "NAVER_CLIENT_NAME", properties["NAVER_CLIENT_NAME"].toString())
        buildConfigField("String", "NAVER_CLIENT_SECRET", properties["NAVER_CLIENT_SECRET"].toString())

        manifestPlaceholders["KAKAO_NATIVE_APP_KEY_MANIFEST"] = properties["kakao.native.app.key.manifest"] as String
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // timber - log
    implementation(libs.timber)

    // navigation
    implementation(libs.navigation.compose)

    // kakao
    implementation(libs.v2.user)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // ViewModel utilities for Compose
    implementation(libs.hilt.navigation.compose)

    // lottie
    implementation(libs.lottie.compose)

    // coroutine
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // naver
    implementation(libs.oauth)
}