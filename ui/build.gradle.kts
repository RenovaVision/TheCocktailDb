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

    viewBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(project(":style"))
    implementation(project(":domain"))
    implementation(Deps.Androidx.material)
    implementation(Deps.Androidx.liveData)
    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.Kotlin.coroutinesAndroid)
    implementation(Deps.Androidx.constraintlayout)
    implementation(Deps.Square.picasso)
}