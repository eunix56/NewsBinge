object Retrofit {
    private const val version = "2.9.0"
    const val retrofit = "com.squareup.retrofit2:retrofit:$version"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"

    private const val moshiKotlinVersion = "1.10.0"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiKotlinVersion"

    private const val okHttpVersion = "4.10.0"
    const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
}