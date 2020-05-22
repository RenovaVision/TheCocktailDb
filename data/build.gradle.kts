plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(project(":domain"))
    implementation(Deps.Square.okhttp)
    implementation(Deps.Square.retrofit)
    implementation(Deps.Square.retrofitConverterMoshi)
    implementation(Deps.Square.moshi)
    implementation(Deps.Square.moshiAdapters)
    implementation(Deps.Google.dagger)
    kapt(Deps.Google.daggerCompiler)
    kapt(Deps.Square.moshiKotlinCodegen)

    testImplementation(Deps.Kotlin.coroutinesTest)
    testImplementation(Deps.Test.mockWebServer)
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.kotlinTest)
}