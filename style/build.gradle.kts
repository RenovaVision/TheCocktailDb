plugins {
    id("com.android.library")
    kotlin("android")
}

android{
    compileSdkVersion(AndroidConfig.compileSdkVersion)
    kotlinOptions{
        jvmTarget  = "1.8"
    }
}

dependencies {
    implementation(Deps.Androidx.material)
    implementation(Deps.Androidx.fragment)
}
