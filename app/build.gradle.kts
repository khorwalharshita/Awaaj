plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" // Required for Kotlin 2.0+
}

android {
    namespace = "com.example.signlanguageapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.signlanguageapp"
        minSdk = 24
        //noinspection EditedTargetSdkVersion
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.0" // Required for Kotlin 2.0+
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    implementation("androidx.compose.material:material-icons-extended:1.6.7")

    // Jetpack Compose BOM (Bill of Materials)
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Navigation (Only keep the latest version)
    implementation("androidx.navigation:navigation-compose:2.8.9")

    // Animations
    implementation("androidx.compose.animation:animation:1.6.0")
    implementation(libs.vision.common)
    implementation(libs.image.labeling.common)
    implementation(libs.image.labeling.default.common)
    implementation(libs.play.services.mlkit.text.recognition.common)
    implementation(libs.play.services.mlkit.text.recognition)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.01.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    implementation ("androidx.compose.ui:ui-text-google-fonts:1.6.4")


    implementation ("com.google.accompanist:accompanist-placeholder-material:0.28.0")

    implementation("org.tensorflow:tensorflow-lite:2.13.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.2")
    implementation ("androidx.camera:camera-camera2:1.3.1")
    implementation ("androidx.camera:camera-lifecycle:1.3.1")
    implementation ("androidx.camera:camera-view:1.3.1")
    implementation ("androidx.camera:camera-core:1.3.1")
    implementation ("androidx.camera:camera-extensions:1.3.1")

    implementation ("androidx.compose.runtime:runtime-livedata:1.6.0")


        // Jetpack Compose (you likely already have these)
        implementation ("androidx.compose.ui:ui:<version>")
        implementation ("androidx.navigation:navigation-compose:<version>")

        // If using ML Kit for fallback (optional)
        implementation ("com.google.mlkit:text-recognition:16.0.0")

        // For image capture
        implementation ("androidx.activity:activity-compose:<version>")

    implementation ("org.tensorflow:tensorflow-lite:2.12.0") // or newer
    implementation ("org.tensorflow:tensorflow-lite-support:0.3.1")
    implementation ("org.tensorflow:tensorflow-lite-gpu:2.12.0") // optional, for acceleration



}

