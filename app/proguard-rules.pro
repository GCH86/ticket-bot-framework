# Android Proguard rules for performance-critical code

# Preserve performance-critical classes
-keep class com.ticket.bot.framework.network.** { *; }
-keep class com.ticket.bot.framework.data.** { *; }
-keep class com.ticket.bot.framework.domain.** { *; }

# Preserve data classes and their properties
-keepclassmembers class com.ticket.bot.framework.data.model.** {
    <init>(...);
    *;
}

# Moshi JSON parsing - preserve annotations and methods
-keepclasseswithmembers class * {
    @com.squareup.moshi.Json <fields>;
}

-keep interface com.squareup.moshi.JsonQualifier
-keepclasseswithmembers class * {
    @com.squareup.moshi.JsonQualifier *;
}

# Kotlin metadata
-keepclassmembers class ** {
    *** toString();
}

-keep class kotlin.Metadata { *; }

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Room database
-keep class androidx.room.** { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }

# Coroutines
-keepclasseswithmembernames class kotlinx.** {
    native <methods>;
}

# Dagger
-dontwarn com.google.errorprone.annotations.**
-keep class dagger.** { *; }
-keep @dagger.** class * { *; }

# Timber Logging
-keep class timber.log.** { *; }

# R8 aggressive optimizations
-optimizations !class/merging/*,!code/simplification/arithmetic,!field/*,!method/marking/private
-optimizeaggressively

# Performance tuning
-repackageclasses
-allowaccessmodification
