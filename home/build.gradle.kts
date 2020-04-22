plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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
    implementation(project(":inject"))
    implementation(project(":utils"))
    implementation(project(":network"))
    implementation(Deps.Androidx.material)
    implementation(Deps.Androidx.fragment)
    implementation(Deps.Androidx.constraintlayout)
    implementation(Deps.Google.dagger)
    kapt(Deps.Google.daggerCompiler)
}