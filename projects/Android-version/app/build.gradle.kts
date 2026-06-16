plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.lukaszjag.diet_tracker_android"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.lukaszjag.diet_tracker_android"
        minSdk = 30
        targetSdk = 36
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // ... your other default dependencies ...

    // Retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson to convert Azure's JSON response into Java Objects
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


}