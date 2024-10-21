package com.shop.marqueapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var autoScrollHandler: Handler
    private lateinit var autoScrollRunnable: Runnable
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager2 = findViewById(R.id.recyclerView)

        // Sample list of items
        val items = mutableListOf<String>()
        for (i in 1..10) {
            items.add("item $i")
        }

        val chunkedItems = items.chunked(3)
        val adapter = MarqueePagerAdapter(chunkedItems)
        viewPager.adapter = adapter

        // Set page margin for peeking next and previous pages
        val marginPx = resources.getDimensionPixelSize(R.dimen.viewpager_page_margin)
        val offsetPx = resources.getDimensionPixelSize(R.dimen.viewpager_offset)

        // Set the vertical page transformer
        viewPager.setPageTransformer(VerticalPageTransformer(marginPx, offsetPx))

//        viewPager.addItemDecoration(MarginItemDecoration(marginPx, offsetPx))

        // Start from a middle item to allow infinite scrolling effect
        viewPager.setCurrentItem(currentPosition, false)


        // Set up automatic scrolling
        autoScrollHandler = Handler(Looper.getMainLooper())
        autoScrollRunnable = object : Runnable {
            override fun run() {
                if(currentPosition == chunkedItems.size-1){
                    autoScrollHandler.removeCallbacks(this)
                }

                currentPosition++
                Log.e("currentPosition", "" + (currentPosition % chunkedItems.size))
                viewPager.setCurrentItem(currentPosition, true)
                autoScrollHandler.postDelayed(this, 1000 * 5) // Adjust delay for auto-scroll speed
            }
        }

        // Start auto-scroll
        autoScrollHandler.postDelayed(autoScrollRunnable, 1000 * 5)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop auto-scroll when the activity is destroyed
        autoScrollHandler.removeCallbacks(autoScrollRunnable)
    }
}

class VerticalPageTransformer(val marginPx: Int, val offsetPx: Int) : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        // Translate page vertically for smooth scrolling
        val offset = position * (page.height + marginPx)
        page.translationY = offset - offsetPx * position

        // Scale pages that are not the focused one
        val scaleFactor = 0.85f + (1 - Math.abs(position)) * 0.15f
        page.scaleX = scaleFactor
        page.scaleY = scaleFactor

        // Adjust opacity to fade out non-focused pages
        page.alpha = 0.5f + (1 - Math.abs(position)) * 0.5f
    }
}

