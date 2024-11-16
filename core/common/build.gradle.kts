plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.plugin.serialization)
}

android {
    val javaVersion: JavaVersion by rootProject.extra

    namespace = "com.tmdbtestapp.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String", "IMAGE_BASE_URL", "\"https://image.tmdb.org/t/p/\"")
        buildConfigField(
            "String",
            "VIDEO_URL",
            "\"https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.retrofit2.retrofit)
}