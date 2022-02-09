package com.autopromaker.projectl

import android.os.Bundle
import android.widget.Toast
import com.autopromaker.adapter.MainViewPagerAdapter
import com.autopromaker.projectl.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : BaseActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var mViewPagerAdapter: MainViewPagerAdapter
    private var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.let {
            setCustomActionBar()
        }

        setUpEvents()
        setValues()
    }

    override fun setValues() {

        mViewPagerAdapter = MainViewPagerAdapter(supportFragmentManager)
        binding.viewPager.adapter = mViewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.apply {
            getTabAt(0)?.setIcon(R.drawable.ic_baseline_home_24)?.text = "메인"
            getTabAt(1)?.setIcon(R.drawable.ic_baseline_menu_24)?.text = "내 정보"
        }

    }

    override fun setUpEvents() {

    }

    private fun setViewPager() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - waitTime >= 1500) {
            waitTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            finish() // 액티비티 종료
        }

    }
}