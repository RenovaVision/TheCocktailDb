plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(Deps.Google.dagger)
    kapt(Deps.Google.daggerCompiler)
    kapt(Deps.Square.moshiKotlinCodegen)
}