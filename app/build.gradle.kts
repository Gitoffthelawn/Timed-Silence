import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinxSerializable)
    alias(libs.plugins.ksp)
}

android {
    namespace = "de.felixnuesse.timedsilence"
    compileSdk = 36

    defaultConfig {
        applicationId = "de.felixnuesse.timedsilence2"
        minSdk = 26
        targetSdk = 36
        versionCode = 10
        versionName = "3.2.0"

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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    lint {
        disable += "MissingTranslation"
        baseline = file("lint-baseline.xml")
    }

    applicationVariants.all {
        outputs.all {
            if(name.contains("release"))
                (this as BaseVariantOutputImpl).outputFileName = "TimedSilence-release-v$versionName.apk"
        }
    }

    // fixes some weird classpath duplication issue.
    configurations {
        implementation.get().exclude(
            mapOf("group" to "org.jetbrains", "module" to "annotations"))
    }

}


dependencies {
    //implementation(fileTree(dir: 'libs', include: ['*.jar']))
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material)
    implementation(libs.balloon)

    implementation(libs.appintro)
    implementation(libs.androidx.preference.ktx)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.core.splashscreen)

    // Room:
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)


    // ListenableFuture for PermissionManger for unused-app-persistence. Both are needed!
    // https://developer.android.com/develop/background-work/background-tasks/asynchronous/listenablefuture
    implementation("com.google.guava:listenablefuture:1.0") // do not upgrade. Only version.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.10.2")
}

