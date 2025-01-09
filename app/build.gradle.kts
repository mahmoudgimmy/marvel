plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.navigation.safeargs.kotlin)
    id("dagger.hilt.android.plugin")
   alias(libs.plugins.ksp)
    id ("kotlin-parcelize")
}

android {
    namespace = "com.example.marvel"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.marvel"
        minSdk = 24
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
            buildConfigField("String", "PUBLIC_KEY",  "\"${project.findProperty("PUBLIC_KEY")}\"")
            buildConfigField("String", "PRIVATE_KEY",  "\"${project.findProperty("PRIVATE_KEY")}\"")
            buildConfigField("String", "BASE_URL", "\"https://gateway.marvel.com/\"")
        }
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("String", "PUBLIC_KEY",  "\"${project.findProperty("PUBLIC_KEY")}\"")
            buildConfigField("String", "PRIVATE_KEY",  "\"${project.findProperty("PRIVATE_KEY")}\"")
            buildConfigField("String", "BASE_URL", "\"https://gateway.marvel.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.paging.runtime)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.hilt.android.v244)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    ksp(libs.hilt.android.compiler.v244)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.androidx.room.paging)
    implementation(libs.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.logging.interceptor)
    implementation (libs.material)
    implementation (libs.glide)


}