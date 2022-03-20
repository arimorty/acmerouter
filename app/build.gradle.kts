plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

importCommonPlugins()
configAndroid()
importCommonDependencies()

android {
    defaultConfig {
        applicationId = Versions.App.id

        javaCompileOptions {
            annotationProcessorOptions {
                argument("room.schemaLocation", "$projectDir/schemas")
                argument("enableParallelEpoxyProcessing", "true")
            }
        }
    }

    buildFeatures {
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.JetpackCompose.compiler
    }

    packagingOptions {
        resources {
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    implementation(Dependencies.JetpackCompose.ui)
    implementation(Dependencies.JetpackCompose.tooling)
    implementation(Dependencies.JetpackCompose.foundation)
    implementation(Dependencies.JetpackCompose.material)
    implementation(Dependencies.JetpackCompose.liveData)
    implementation(Dependencies.JetpackCompose.activity)
    implementation(Dependencies.JetpackCompose.appCompatTheme)
    implementation(Dependencies.JetpackCompose.swipeRefresh)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}