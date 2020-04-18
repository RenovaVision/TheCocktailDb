plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(AndroidConfig.compileSdkVersion)
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
    implementation(project(":ui"))
    implementation(project(":network"))
    implementation(Deps.Androidx.material)
    implementation(Deps.Androidx.fragment)
    implementation(Deps.Androidx.recyclerview)
    implementation(Deps.Androidx.constraintlayout)
    implementation(Deps.Square.picasso)
    implementation(Deps.Google.dagger)
    kapt(Deps.Google.daggerCompiler)
}