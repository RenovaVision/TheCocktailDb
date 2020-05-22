plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(AndroidConfig.compileSdkVersion)
    defaultConfig {
        minSdkVersion(AndroidConfig.minSdkVersion)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        isEnabled = true
    }
    testOptions {
        animationsDisabled = true
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(project(":style"))
    implementation(project(":inject"))
    implementation(project(":ui"))
    implementation(project(":domain"))
    implementation(Deps.Androidx.material)
    implementation(Deps.Androidx.fragment)
    implementation(Deps.Androidx.recyclerview)
    implementation(Deps.Androidx.constraintlayout)
    implementation(Deps.Square.picasso)
    implementation(Deps.Google.dagger)
    kapt(Deps.Google.daggerCompiler)

    testImplementation(project(":ui-testutils"))
    testImplementation(Deps.Androidx.espressoContrib)
    testImplementation(Deps.Androidx.espressoCore)
    testImplementation(Deps.Test.kotlinTest)
    testImplementation(Deps.Androidx.coreTesting)
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Androidx.junitKtx)
    testImplementation(Deps.Androidx.testRules)
    testImplementation(Deps.Androidx.testRunner)
    testImplementation(Deps.Test.robolectric)
    testImplementation(Deps.Test.podam)
}