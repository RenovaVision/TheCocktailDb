plugins {
    kotlin("jvm")
}

dependencies {
    implementation(Deps.Javax.inject)
    implementation(Deps.Kotlin.coroutines)
    testImplementation(Deps.Test.mockito)
    testImplementation(Deps.Test.mockitoKotlin)
    testImplementation(Deps.Test.junit)
    testImplementation(Deps.Test.kotlinTest)
    testImplementation(Deps.Kotlin.coroutinesTest)
    testImplementation(Deps.Test.podam)
}