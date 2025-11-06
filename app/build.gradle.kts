plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.my_app_music"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.my_app_music"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // 🧿 Hiển thị ảnh bo tròn
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // 🔘 Indicator cho ViewPager hoặc Slider
    implementation("me.relex:circleindicator:2.1.6")

    // 🧩 Supabase SDK chính thức
    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.3.0")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.3.0")
    implementation("io.github.jan-tennert.supabase:storage-kt:2.3.0")
    implementation("io.github.jan-tennert.supabase:realtime-kt:2.3.0")

    // 🌐 Gửi request API (Java/Kotlin)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // ⚡ Gửi request đơn giản (Volley)
    implementation("com.android.volley:volley:1.2.1")

    // 🎵 Thư viện load ảnh (Glide)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // 🚀 Thư viện Retrofit (cần để chạy ApiClient)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
