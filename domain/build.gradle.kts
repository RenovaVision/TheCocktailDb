plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(Deps.Google.dagger)
    implementation(Deps.Kotlin.coroutines)
    kapt(Deps.Google.daggerCompiler)
    kapt(Deps.Square.moshiKotlinCodegen)

    testImplementation(Deps.Test.mockito)
    testImplementation(Deps.Test.mockitoKotlin)
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.kotlinTest)
    testImplementation(Deps.Kotlin.coroutinesTest)
}