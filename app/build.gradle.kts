plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("tmdb_test_key")
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
    }
    val javaVersion: JavaVersion by rootProject.extra

    namespace = "com.tmdbtestapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tmdbtestapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("debug")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtension.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:theme"))
    implementation(project(":core:common"))
    implementation(project(":core:presentation"))
    implementation(project(":domain"))
    implementation(project(":di"))

    implementation(libs.androidx.core.ktx)

    // Hilt
    implementation(libs.hilt.core)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // Material
    implementation(libs.androidx.tv.material)
    implementation(libs.androidx.compose.material)

    // Coil
    implementation(libs.io.coil.compose)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines)

    // Media 3
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.androidx.media3.hls)
    implementation(libs.androidx.media3.datasource)
}