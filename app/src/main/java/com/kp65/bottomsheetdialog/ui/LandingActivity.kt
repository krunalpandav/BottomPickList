package com.kp65.bottomsheetdialog.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kp65.bottomsheetdialog.databinding.ActivityLandingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingActivity : AppCompatActivity() {

    lateinit var landingBinding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landingBinding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(landingBinding.root)



    }

}