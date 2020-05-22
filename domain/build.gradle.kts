plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(Deps.Google.dagger)
    implementation(Deps.Kotlin.coroutines)
    kapt(Deps.Google.daggerCompiler)
    kapt(Deps.Square.moshiKotlinCodegen)
}