package com.cts.mywall.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cts.mywall.R

class WallPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpost)


        val mainFragment = WallPostFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.myFragment, mainFragment)
            .commit()
    }
}
