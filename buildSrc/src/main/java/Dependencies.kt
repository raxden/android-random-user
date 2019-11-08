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
    val dataRepository = ":data:repository"

    val featureCommon = ":features:common"
    val featureHome = ":features:home"
    val featureDetail = ":features:detail"
}

object Releases {
    val versionCode = 1
    val versionName = "1.0"
}

object Versions {
    val minSdk = 21
    val compileSdk = 28
    val targetSdk = 28
    val kotlin = "1.3.50"
    val gradle = "3.5.1"
    val room = "2.2.0"                      // https://developer.android.com/jetpack/androidx/releases/room
    val gson = "2.8.6"                      // https://github.com/google/gson
    val okHttp = "4.2.1"                    // https://github.com/square/okhttp
    val retrofit = "2.6.2"                  // https://github.com/square/retrofit
    val atsl = "1.1.0"
    val archCoreTest = "2.0.0"
    val dagger = "2.22.1"                   // https://github.com/google/dagger
    val threetenabp = "1.2.0"               // https://github.com/JakeWharton/ThreeTenABP
    val timber = "4.7.1"                    // https://github.com/JakeWharton/timber
    val glide = "4.9.0"                     // https://github.com/bumptech/glide
    val constraintLayout = "1.1.3"
    val swiperefreshlayout = "1.0.0"        // https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout
    val material = "1.1.0-beta01"
    val rxAndroid = "2.1.1"
    val rxKotlin = "2.3.0"
    val fragmentTest = "1.1.0"
    val mockk = "1.9.2"
    val databinding = "3.3.2"
    val espressoCore = "3.1.0"
    val leakcanary = "2.0-beta-3"
}

object KotlinLibraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object AndroidLibraries {
    // ANDROID
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val material = "com.google.android.material:material:${Versions.material}"
    val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
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

    // Glide
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    // Timber
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Threetenabp
    val threetenabp = "com.jakewharton.threetenabp:threetenabp:${Versions.threetenabp}"

    // Leakcanary
    val leakcanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary}"
}

object TestLibraries {
    // ANDROID TEST
    val archCoreTest = "androidx.arch.core:core-testing:${Versions.archCoreTest}"
    val atslJunit = "androidx.test.ext:junit:${Versions.atsl}"
    val atslRunner = "androidx.test:runner:${Versions.atsl}"
    val atslRules = "androidx.test:rules:${Versions.atsl}"
    // MOCKK
    val mockkCore = "io.mockk:mockk:${Versions.mockk}"
    val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    // MOCK WEBSERVER
    val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
}

object TestAndroidLibraries {
    val fragmentTest = "androidx.fragment:fragment-testing:${Versions.fragmentTest}"
    // ESPRESSO
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espressoCore}"
    // DATA BINDING
    val databinding = "androidx.databinding:databinding-compiler:${Versions.databinding}"
}