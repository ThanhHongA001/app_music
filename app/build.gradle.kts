plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.app_music"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.app_music"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true // ✅ Bật ViewBinding để dùng binding trong Activity/Fragment
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

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // 🧱 Thư viện cơ bản Android
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // 🧿 Hiển thị ảnh bo tròn
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // 🔘 Indicator cho ViewPager hoặc Slider
    implementation("me.relex:circleindicator:2.1.6")

    // 🧩 Supabase SDK chính thức (by jan-tennert)
    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.3.0")     // Xác thực người dùng
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.3.0")  // Kết nối database
    implementation("io.github.jan-tennert.supabase:storage-kt:2.3.0")    // Lưu trữ file
    implementation("io.github.jan-tennert.supabase:realtime-kt:2.3.0")   // Realtime event

    // ✅ Không dùng dòng sai này nữa:
    // implementation("io.supabase:supabase-kt:2.0.2")

    // 🌐 Gửi request API (Java/Kotlin)
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")

    // ⚡ Gửi request đơn giản (Volley)
    implementation("com.android.volley:volley:1.2.1")

    // 🎵 Thư viện load ảnh (Glide)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // 🧪 Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
