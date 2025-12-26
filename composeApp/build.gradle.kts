import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()

    js {
        browser()
        binaries.executable()
        useEsModules()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
        useEsModules()
    }

    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("io.ktor:ktor-client-okhttp:3.3.3")

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(projects.shared)
            // 序列化依赖
            implementation(libs.kotlinx.serialization.json)
            // 导航依赖
            implementation(libs.navigation.compose)
            implementation("io.github.vinceglb:filekit-core:0.12.0")
            implementation("io.github.vinceglb:filekit-dialogs:0.12.0")
            implementation("io.github.vinceglb:filekit-dialogs-compose:0.12.0")
            // Ktor Client 核心 - 只需要这些!
            implementation("io.ktor:ktor-client-core:3.3.3")
            implementation("io.ktor:ktor-client-content-negotiation:3.3.3")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.3.3")
            implementation("io.ktor:ktor-client-logging:3.3.3")
//            // 协程和序列化
//            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
//            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation("io.ktor:ktor-client-okhttp:3.3.3")
        }
        // 添加 JS 和 Wasm 配置
        jsMain.dependencies {
            // JS 特定依赖（如果有）
            implementation("io.ktor:ktor-client-js:3.3.3")
            implementation("org.jetbrains.kotlin-wrappers:kotlin-browser:2025.12.8")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

        }

        wasmJsMain.dependencies {
            // Wasm 特定依赖（如果有）
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation("io.ktor:ktor-client-js:3.3.3")
            implementation("org.jetbrains.kotlin-wrappers:kotlin-browser:2025.12.8")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

        }


    }
}

android {
    namespace = "com.jthl.morekmptwo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.jthl.morekmptwo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "com.jthl.morekmptwo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.jthl.morekmptwo"
            packageVersion = "1.0.0"
        }
    }
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
compose.resources {
    publicResClass = true
    packageOfResClass = "com.jthl.morekmptwo.resources"
    generateResClass = always
}
