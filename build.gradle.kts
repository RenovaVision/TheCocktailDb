// Top-level build file where you can add configuration options common to all sub-projects/modules.

import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        jcenter()
    }
}
plugins {
    base
    id("com.android.application") version "3.6.1" apply false
    kotlin("android") version PluginVersions.kotlin apply false
    kotlin("kapt") version PluginVersions.kotlin apply false
    id("androidx.navigation.safeargs.kotlin") version PluginVersions.nav apply false
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}

subprojects {
    plugins.withType<JavaPlugin> {
        configure<JavaPluginExtension> {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
    plugins.withType<KotlinPluginWrapper> {
        tasks.withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
        dependencies {
            "implementation"(kotlin("stdlib-jdk8"))
        }
    }
    plugins.withType<KotlinAndroidPluginWrapper> {
        tasks.withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "1.8"
        }
        dependencies {
            "implementation"(kotlin("stdlib-jdk8"))
            "implementation"(Deps.Androidx.core)
        }
    }
}