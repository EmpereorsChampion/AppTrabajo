plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.apptrabajo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.apptrabajo"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom)) // Usar BOM para manejar versiones de Compose
    implementation(libs.androidx.ui) // Composable UI
    implementation(libs.androidx.ui.graphics) // Composición gráfica
    implementation(libs.androidx.ui.tooling.preview) // Herramientas para la vista previa
    implementation(libs.androidx.material3) // Material Design 3

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.0") // Dependencia de navegación

    // Otros componentes
    implementation(libs.androidx.activity.compose) // Composable para actividades
    implementation(libs.androidx.lifecycle.runtime.ktx) // Extensiones de ciclo de vida
    implementation(libs.androidx.core.ktx) // Extensiones para el núcleo de Android

    // Pruebas
    testImplementation(libs.junit) // JUnit para pruebas unitarias
    androidTestImplementation(libs.androidx.junit) // JUnit para pruebas de Android
    androidTestImplementation(libs.androidx.espresso.core) // Espresso para pruebas de UI
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM para pruebas de Compose
    androidTestImplementation(libs.androidx.ui.test.junit4) // Pruebas de UI con JUnit4
    debugImplementation(libs.androidx.ui.tooling) // Herramientas de depuración para Compose
    debugImplementation(libs.androidx.ui.test.manifest) // Manifest para pruebas de UI
}
