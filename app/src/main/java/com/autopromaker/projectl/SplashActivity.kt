package com.autopromaker.projectl

import android.os.Bundle
import com.autopromaker.projectl.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setValues()
        setUpEvents()
    }

    override fun setValues() {

    }

    override fun setUpEvents() {

    }
}