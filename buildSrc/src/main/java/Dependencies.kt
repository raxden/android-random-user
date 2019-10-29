object ApplicationId {
    val id = "com.randomuser.app"
}

object Modules {
    val app = ":app"

    val core = ":core"
    val navigation = ":navigation"

    val commonAndroid = ":common:android"
    val commonDagger = ":common:dagger"
    val commonTest = ":common:test"

    val domain = ":domain"

    val dataLocal = ":data:local"
    val dataRemote = ":data:remote"
    val dataModel = ":data:model"
    val dataRepository = ":data:repository"

    val featureSplash = ":features:splash"
    val featureLogin = ":features:login"
    val featureHome = ":features:home"
    val featureDetail = ":features:detail"
}

object Releases {
    val versionCode = 1
    val versionName = "1.0"
}

object Sdk {
}

object Versions {
    val minSdk = 21
    val compileSdk = 28
    val targetSdk = 28
    val kotlin = "1.3.50"
    val appCompat = "1.1.0-rc01"
    val gradle = "3.5.1"
    val fabric = "1.28.1"
    val room = "2.2.0"                      // https://developer.android.com/jetpack/androidx/releases/room
    val gson = "2.8.6"                      // https://github.com/google/gson
    val okHttp = "4.2.1"                    // https://github.com/square/okhttp
    val retrofit = "2.6.2"                  // https://github.com/square/retrofit
    val atsl = "1.1.0"
    val archCoreTest = "2.0.0"
    val dagger = "2.22.1"                   // https://github.com/google/dagger
    val threetenabp = "1.2.0"               // https://github.com/JakeWharton/ThreeTenABP
    val timber = "4.7.1"                    // https://github.com/JakeWharton/timber
    val mockito = "2.7.1"
    val lifecycle = "2.2.0-beta01"
    val constraintLayout = "1.1.3"
    val material = "1.1.0-beta01"
    val rxAndroid = "2.1.1"
    val rxKotlin = "2.3.0"
    val robolectric = "4.2.1"
}

object KotlinLibraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object AndroidLibraries {
    // ANDROID
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val material = "com.google.android.material:material:${Versions.material}"
}

object Libraries {
    // Dagger
    val daggerAndroid = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    val daggerProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"

    // Room
    val roomRunTime = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    val roomRX = "androidx.room:room-rxjava2:${Versions.room}"

    // Retrofit
    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    val httpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

    // RX
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"

    //Lifecycle
    val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"

    // Timber
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Threetenabp
    val threetenabp = "com.jakewharton.threetenabp:threetenabp:${Versions.threetenabp}"
}

object TestLibraries {
    // ANDROID TEST
    val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTest}"
    val atslJunit = "androidx.test.ext:junit:${Versions.atsl}"
    val atslRunner = "androidx.test:runner:${Versions.atsl}"
    val atslRules = "androidx.test:rules:${Versions.atsl}"
    // MOCKITO
    val mockCore = "org.mockito:mockito-core:${Versions.mockito}"
    val mockAndroid = "org.mockito:mockito-android:${Versions.mockito}"
    // MOCK WEBSERVER
    val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"

    val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
}