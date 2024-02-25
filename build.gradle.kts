import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlin.cocoapods).apply(false)
}

subprojects {
    afterEvaluate {
        configureSafe<KotlinMultiplatformExtension> {
            jvmToolchain(17)
            androidTarget {
                jvmToolchain(17)
            }
        }

        configureSafe<KotlinAndroidProjectExtension> {
            jvmToolchain(17)
        }
    }
}

inline fun <reified T : Any> Project.configureSafe(noinline action: T.() -> Unit) {
    if (extensions.findByType(T::class) != null) configure<T>(action)
}
