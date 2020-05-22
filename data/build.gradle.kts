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
}

dependencies {
    implementation(project(":domain"))
    implementation(Deps.Square.okhttp)
    implementation(Deps.Square.retrofit)
    implementation(Deps.Kotlin.coroutines)
    implementation(Deps.Square.retrofitConverterMoshi)
    implementation(Deps.Square.moshi)
    implementation(Deps.Square.moshiAdapters)
    implementation(Deps.Google.dagger)
    implementation(Deps.Database.room)
    implementation(Deps.Database.roomKtx)
    kapt(Deps.Database.roomCompiler)
    kapt(Deps.Google.daggerCompiler)
    kapt(Deps.Square.moshiKotlinCodegen)

    testImplementation(Deps.Kotlin.coroutinesTest)
    testImplementation(Deps.Test.mockWebServer)
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.kotlinTest)
}