package com.cts.mywall.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cts.mywall.R
import com.cts.mywall.viewmodel.WallViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mainFragment = MainFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.myFragment, mainFragment)
            .commit()
    }
}
