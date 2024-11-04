import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.android.master.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        buildConfigField("String", "KAKAO_API_KEY", getProperty("KAKAO_API_KEY"))
        manifestPlaceholders["kakaoKey"] = getProperty("KAKAO_API_KEY")
        buildConfigField("String", "NAVER_CLIENT_ID", getProperty("NAVER_CLIENT_ID"))
        buildConfigField("String", "NAVER_CLIENT_NAME", getProperty("NAVER_CLIENT_NAME"))
        buildConfigField("String", "NAVER_CLIENT_SECRET", getProperty("NAVER_CLIENT_SECRET"))
        buildConfigField("String", "GOOGLE_CLIENT_ID", getProperty("GOOGLE_CLIENT_ID"))
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
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // navigation
    implementation(libs.navigation)
    implementation(libs.hilt.navigation.compose)

    // gson
    implementation(libs.gson)

    // kakao login
    implementation(libs.kakao.login)

    // naver login
    implementation(libs.naver.login)

    // gms
    implementation(libs.gms.classpath)
    implementation(libs.gms.auth)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
}

fun getProperty(propertyKey: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}