@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.ktorfit) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.serialization) apply false
}
true