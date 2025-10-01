import org.gradle.kotlin.dsl.androidTestImplementation
import org.gradle.kotlin.dsl.testImplementation
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
}

android {
    namespace = "com.sergokuzneczow.domain"
    compileSdk = 36

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

kotlin {
    explicitApi = ExplicitApiMode.Strict
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:models"))
    implementation(project(":core:utilities"))
    // Kotlin
    implementation(libs.kotlinx.coroutines.core)
    // Dagger
    implementation(libs.google.dagger)
    ksp(libs.google.dagger.compiler)



//    testImplementation("junit:junit:4.13.2")
//    testImplementation("org.mockito:mockito-core:5.20.0")
//    testImplementation("androidx.arch.core:core-testing:2.2.0")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
//    testImplementation("app.cash.turbine:turbine:1.2.1")
//    testImplementation(kotlin("test"))

    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:6.0.0")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation( "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
    testImplementation("app.cash.turbine:turbine:1.2.1")

    androidTestImplementation ("androidx.test.ext:junit:1.3.0")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.7.0")
}