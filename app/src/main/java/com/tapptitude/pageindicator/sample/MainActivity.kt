package com.tapptitude.pageindicator.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.tapptitude.pageindicator.sample.databinding.ActivityMainBinding
import com.tapptitude.pageindicator.sample.util.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.pageIndicator.initPageIndicator(DEFAULT_INDICATORS_COUNT, indicatorSpacing = DEFAULT_INDICATOR_SPACING)

        binding.previousBtn.setOnClickListener {
            binding.pageIndicator.previous()
        }

        binding.nextBtn.setOnClickListener {
            binding.pageIndicator.next()
        }

        // TODO: The goal in the end is to be able to set any property without manually having to set any dependant ones from outside library code
        binding.rotateIndicatorBtn.setOnClickListener {
            val currentOrientation = binding.pageIndicator.pageIndicatorOrientation

            if (currentOrientation == HORIZONTAL) {
                binding.pageIndicator.setOrientation(VERTICAL)
            } else {
                binding.pageIndicator.setOrientation(HORIZONTAL)
            }
            binding.pageIndicator.setIndicatorSpacing(DEFAULT_INDICATOR_SPACING)
        }
    }

    companion object {
        private const val DEFAULT_INDICATORS_COUNT = 7
        private const val DEFAULT_INDICATOR_SPACING = 12
    }
}