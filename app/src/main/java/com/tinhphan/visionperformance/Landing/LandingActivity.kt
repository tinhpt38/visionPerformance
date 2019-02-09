package com.tinhphan.visionperformance.Landing

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.tinhphan.visionperformance.Cameraman.CameramanActivity
import com.tinhphan.visionperformance.R

class LandingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        openCameraman()
    }

    private fun openCameraman() {
        Handler().postDelayed(Runnable {
            val intent = Intent(this, CameramanActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }, 2000)
    }
}
