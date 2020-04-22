object Deps {
    object Androidx {
        const val material = "com.google.android.material:material:1.1.0"
        const val core = "androidx.core:core-ktx:1.2.0"
        const val appcompat = "androidx.appcompat:appcompat:1.1.0"
        const val activity = "androidx.activity:activity-ktx:1.1.0"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.0.0-beta4"
        const val recyclerview = "androidx.recyclerview:recyclerview:1.1.0"
        const val navFrag = "androidx.navigation:navigation-fragment-ktx:${PluginVersions.nav}"
    }

    object Google {
        const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
        const val daggerAndroidProcessor =
            "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    }

    object Square {
        const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
        const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
        const val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitConverterMoshi =
            "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val picasso = "com.squareup.picasso:picasso:2.71828"
    }
}

object PluginVersions {
    const val kotlin = "1.3.71"
    const val nav = "2.2.1"
}

object AndroidConfig {
    const val compileSdkVersion = 29
    const val targetSdkVersion = 29
    const val minSdkVersion = 21
}

/**
 * Version substrings for dependencies with multiple artifacts.
 *
 * When a dependency is made up of multiple artifacts whose versions are tied together then it
 * makes sense to deduplicate the version part of the dependency string into [Versions]. This way we
 * are sure that those artifacts are always updated together and their versions will not go "out of
 * sync".
 */
private object Versions {
    const val dagger = "2.27"
    const val moshi = "1.9.2"
    const val fragment = "1.2.3"
    const val okhttp = "4.4.1"
    const val retrofit = "2.8.1"
}
