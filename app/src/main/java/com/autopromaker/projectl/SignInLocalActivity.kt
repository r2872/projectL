package com.autopromaker.projectl

import android.os.Bundle
import com.autopromaker.projectl.databinding.ActivitySignInLocalBinding

class SignInLocalActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySignInLocalBinding.inflate(layoutInflater)
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