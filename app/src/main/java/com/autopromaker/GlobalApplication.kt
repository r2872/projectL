package com.autopromaker

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "eae9a58ac42320a7d7ab65ede393417c")
    }
}