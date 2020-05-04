pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.application" -> useModule("com.android.tools.build:gradle:${requested.version}")
                "androidx.navigation.safeargs.kotlin" -> useModule("androidx.navigation:navigation-safe-args-gradle-plugin:${requested.version}")
            }
        }
    }
}

rootProject.name = "TheCocktailDb"

include(
    ":app",
    ":inject",
    ":home",
    ":category",
    ":ingredients",
    ":cocktails",
    ":data",
    ":domain",
    ":utils",
    ":ui",
    ":style"
)