plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.diaryloveproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.diaryloveproject"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

   // implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.constraintlayout)
    implementation(libs.circleimageview)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.appcompat)
    implementation(libs.androidx.gridlayout)
    testImplementation(libs.junit)
    implementation(libs.gson)
    implementation (libs.retrofit)
    implementation (libs.retrofit.converter.gson)
    // Core
    implementation(libs.androidx.recyclerview)
    // Image loading
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}