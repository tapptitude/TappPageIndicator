package com.tapptitude.pageindicator.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tapptitude.pageindicator.sample.databinding.ActivityMainBinding
import com.tapptitude.pageindicator.sample.util.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.pageIndicator.initPageIndicator(7, indicatorSpacing = 12)

        binding.previousBtn.setOnClickListener {
            binding.pageIndicator.previous()
        }

        binding.nextBtn.setOnClickListener {
            binding.pageIndicator.next()
        }
    }
}