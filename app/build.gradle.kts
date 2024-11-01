plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.freedman.parsingtool"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.freedman.parsingtool"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.jackson.databind)
    implementation(libs.opencsv)
    testImplementation("org.mockito:mockito-core:5.14.2")

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    implementation (libs.recyclerview)

    testImplementation(libs.junit)
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)



}

