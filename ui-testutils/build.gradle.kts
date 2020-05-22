plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(AndroidConfig.compileSdkVersion)
    defaultConfig {
        minSdkVersion(AndroidConfig.minSdkVersion)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Deps.Androidx.appcompat)
    implementation(Deps.Androidx.activity)
    implementation(Deps.Androidx.coreTesting)
    implementation(Deps.Androidx.testRules)
    implementation(Deps.Test.kotlinTest)
}