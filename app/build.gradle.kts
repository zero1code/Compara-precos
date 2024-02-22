import com.google.firebase.crashlytics.buildtools.gradle.CrashlyticsExtension
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("comparaprecos.android.application")
    id("comparaprecos.android.application.compose")
    id("comparaprecos.android.application.jacoco")
    id("comparaprecos.android.hilt")
    id("comparaprecos.android.application.firebase")
}

android {
    namespace = "com.z1.comparaprecos"

    defaultConfig {
        applicationId = "com.z1.comparaprecos"
        versionCode = 3
        versionName = "1.0.2"

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        manifestPlaceholders["ADMOB_APP_ID"] = getProperties("ADMOB_APP_ID")

        buildConfigField("String", "DEVICE_TEST_AD_ID", getProperties("DEVICE_TEST_AD_ID"))

        tasks.register("printVersionInfo") {
            doLast {
                println("VersionName: $versionName")
                println("VersionCode: $versionCode")
            }
        }
    }

    buildFeatures {
            buildConfig = true
    }

    signingConfigs {
        create("release") {
            storeFile = file("../keystore.jks")
            keyAlias = System.getenv("SIGNING_KEY_ALIAS")
            keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
            storePassword = System.getenv("SIGNING_STORE_PASSWORD")
        }
    }

    buildTypes {
        debug{
            buildConfigField("String", "ADMOB_OPEN_APP_ID", getProperties("ADMOB_OPEN_APP_TEST_ID"))
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            buildConfigField("String", "ADMOB_OPEN_APP_ID", getProperties("ADMOB_OPEN_APP_ID"))
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")

            configure<CrashlyticsExtension> {
                mappingFileUploadEnabled = true
            }
        }
    }

//    configurations {
//        debugImplementation.configure {
//            exclude(group = "junit", module = "junit")
//        }
//    }
}

dependencies {
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.compose)
    implementation(libs.play.services.ads)
//    implementation(libs.leak.canary)

    implementation(project(":core:common"))
    implementation(project(":core:datastore"))
    implementation(project(":core:database"))
    implementation(project(":core:model"))
    implementation(project(":core:navigation"))
    implementation(project(":core:testing"))

}

fun getProperties(propertiesName: String): String {
    try {
        val propsFile = rootProject.file("local.properties")
        if (propsFile.exists()) {
            val properties = Properties()
            properties.load(FileInputStream(propsFile))
            return properties.getProperty(propertiesName)
        }
        return ""
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}
