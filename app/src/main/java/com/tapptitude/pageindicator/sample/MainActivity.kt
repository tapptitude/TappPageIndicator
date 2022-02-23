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

        binding.pageIndicator.initPageIndicator(7, indicatorSpacing = 12)

        binding.previousBtn.setOnClickListener {
            binding.pageIndicator.previous()
        }

        binding.nextBtn.setOnClickListener {
            binding.pageIndicator.next()
        }

        binding.rotateIndicatorBtn.setOnClickListener {
            val currentOrientation = binding.pageIndicator.orientation

            if (currentOrientation == HORIZONTAL) {
                binding.pageIndicator.setOrientation(VERTICAL)
            } else {
                binding.pageIndicator.setOrientation(HORIZONTAL)
            }
            binding.pageIndicator.setIndicatorSpacing(12)
        }
    }
}