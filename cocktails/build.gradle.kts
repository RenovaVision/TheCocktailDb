plugins {
    id("com.android.library")
    kotlin("android")
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
    implementation(project(":inject"))
    implementation(project(":ui"))
    implementation(project(":domain"))
    implementation(Deps.Androidx.material)
    implementation(Deps.Androidx.fragment)
    implementation(Deps.Androidx.recyclerview)
    implementation(Deps.Androidx.constraintlayout)
    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.Kotlin.coroutinesAndroid)
    implementation(Deps.Square.picasso)
    implementation(Deps.Javax.inject)

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
    testImplementation(Deps.Test.mockito)
    testImplementation(Deps.Test.mockitoKotlin)
    testImplementation(Deps.Kotlin.coroutinesTest)
}