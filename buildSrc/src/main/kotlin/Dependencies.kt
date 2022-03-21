import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate

object Dependencies {

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradle}"
    const val material = "com.google.android.material:material:${Versions.material}"

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appCompat}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.AndroidX.core}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.constraintLayout}"
        const val swipeRefreshLayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.AndroidX.swipeRefreshLayout}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val runner = "androidx.test:runner:${Versions.testRunner}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val mockk = "io.mockk:mockk:${Versions.mockk}"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    }

    object DaggerHilt {
        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.DependencyInjection.daggerHilt}"
        const val core = "com.google.dagger:hilt-android:${Versions.DependencyInjection.daggerHilt}"
        const val coreViewModel = "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.DependencyInjection.daggerHiltJetpack}"
        const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.DependencyInjection.daggerHilt}"
        const val fragment = "androidx.fragment:fragment-ktx:1.3.0-alpha07"
    }

    object JetpackCompose {
        const val ui = "androidx.compose.ui:ui:${Versions.JetpackCompose.jetpackCompose}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.JetpackCompose.jetpackCompose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.JetpackCompose.jetpackCompose}"
        const val material = "androidx.compose.material:material:${Versions.JetpackCompose.jetpackCompose}"
        const val liveData = "androidx.compose.runtime:runtime-livedata:${Versions.JetpackCompose.jetpackCompose}"
        const val activity = "androidx.activity:activity-compose:${Versions.JetpackCompose.activity}"
        const val appCompatTheme = "com.google.accompanist:accompanist-appcompat-theme:${Versions.JetpackCompose.appCompatTheme}"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:${Versions.JetpackCompose.swipeRefresh}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val annotation = "androidx.room:room-compiler:${Versions.room}"
        const val ktx = "androidx.room:room-ktx:${Versions.room}"
    }
}

fun Project.importCommonPlugins() {
    plugins.apply("kotlin-android")
    plugins.apply("kotlin-android-extensions")
    plugins.apply("kotlin-kapt")
}

// apply common plugin
fun Project.importCommonDependencies() {
    dependencies {

        // The two following syntax is applicable
        // source: https://github.com/gradle/kotlin-dsl-samples/issues/843
        "implementation"(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
        "implementation"(Dependencies.Kotlin.stdLib)

        val implementation by configurations
        val testImplementation by configurations
        val androidTestImplementation by configurations

        implementation(Dependencies.AndroidX.appCompat)
        implementation(Dependencies.AndroidX.coreKtx)
        implementation(Dependencies.AndroidX.constraintLayout)
        implementation(Dependencies.AndroidX.swipeRefreshLayout)
        implementation(Dependencies.material)

        implementation(Dependencies.DaggerHilt.core)
        implementation(Dependencies.DaggerHilt.coreViewModel)
        implementation(Dependencies.DaggerHilt.fragment)
        implementation(Dependencies.DaggerHilt.compiler)
        "kapt"(Dependencies.DaggerHilt.compiler)

        implementation(Dependencies.Retrofit.core)
        implementation(Dependencies.Retrofit.gsonConverter)

        implementation(Dependencies.Room.annotation)
        implementation(Dependencies.Room.runtime)
        implementation(Dependencies.Room.ktx)
        "kapt"(Dependencies.Room.annotation)

        testImplementation(Dependencies.Test.junit)
        androidTestImplementation(Dependencies.Test.runner)
        androidTestImplementation(Dependencies.Test.espressoCore)
        testImplementation(Dependencies.Test.mockk)

        testImplementation(Dependencies.Test.coroutines)
        androidTestImplementation(Dependencies.Test.coroutines)
    }
}