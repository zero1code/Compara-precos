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
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "DEVICE_TEST_AD_ID", getProperties("DEVICE_TEST_AD_ID"))
    }

    buildTypes {
        debug{
            buildConfigField("String", "ADMOB_OPEN_APP_ID", getProperties("ADMOB_OPEN_APP_TEST_ID"))
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        release {
            buildConfigField("String", "ADMOB_OPEN_APP_ID", getProperties("ADMOB_OPEN_APP_ID"))
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    buildFeatures {
        buildConfig = true
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
    implementation(project(":core:model"))
    implementation(project(":core:navigation"))
    implementation(project(":core:testing"))

}

fun getProperties(propertiesName: String): String {
    val propsFile = rootProject.file("local.properties")
    if (propsFile.exists()) {
        val properties = Properties()
        properties.load(FileInputStream(propsFile))
        return properties.getProperty(propertiesName)
    }
    return ""
}