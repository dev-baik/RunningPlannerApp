plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation(libs.javax.inject)

    // coroutine
    implementation(libs.coroutines.core)

    // paging
    implementation(libs.paging.common)
}