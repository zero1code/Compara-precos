plugins {

    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
}