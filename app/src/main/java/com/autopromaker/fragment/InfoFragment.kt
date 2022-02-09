package com.autopromaker.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.autopromaker.projectl.R
import com.autopromaker.projectl.databinding.FragmentInfoBinding

class InfoFragment : BaseFragment() {

    private val binding: FragmentInfoBinding by lazy {
        FragmentInfoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupEvents()
        setValues()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}