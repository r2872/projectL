package com.autopromaker.projectl

import android.os.Bundle
import com.autopromaker.projectl.databinding.ActivitySignInSelectBinding

class SignInSelectActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySignInSelectBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setValues()
        setUpEvents()
    }

    override fun setValues() {

    }

    override fun setUpEvents() = with(binding) {

    }
}