object Version{
    const val retrofit = "2.9.0"
    const val coroutines = "1.7.1"
    const val viewbindingpropertydelegate = "1.5.9"
    const val Glide = "4.15.0"
    const val annotationProcessor = "4.12.0"
    const val daggerHilt = "2.46.1"
    const val navigation = "2.7.5"
    const val circleindicator = "2.1.6"
    const val dotsindicator = "4.3"
    const val viewpager2 = "1.0.0"
    const val okhttp = "5.0.0-alpha.2"
    const val interceptor = "4.11.0"
    const val lottieAnimations = "3.4.2"


}
object Retrofit{
    const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
    const val json = "com.squareup.retrofit2:converter-gson:${Version.retrofit}"
}

object Coroutines {
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
}

object Viewbindingpropertydelegate {
    const val viewbindingpropertydelegate = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${Version.viewbindingpropertydelegate}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Version.Glide}"
}

object AnnotationProcessor {
    const val annotationProcessor = "com.github.bumptech.glide:compiler:${Version.annotationProcessor}"
}

object DaggerHilt{
    //hilt
    const val android = "com.google.dagger:hilt-android:${Version.daggerHilt}"
    const val compiler = "com.google.dagger:hilt-compiler:${Version.daggerHilt}"
}


object Navigation {
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
    const val navigationKtx = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"
}
object DotsIndicator {
    const val circleindicator = "me.relex:circleindicator:${Version.circleindicator}"
    const val dotsindicator = "com.tbuonomo:dotsindicator:${Version.dotsindicator}"
}

object Viewpager2 {
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Version.viewpager2}"

}

object Okhttp {
    const val okhttp = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Version.interceptor}"
}
object LottieAnimations{
    const val lottieAnimations = "com.airbnb.android:lottie:${Version.lottieAnimations}"
}
object Java {
    const val library = "java-library"
}