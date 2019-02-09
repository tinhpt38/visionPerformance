package com.tinhphan.visionperformance

import android.app.Application
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.services.vision.v1.Vision
import com.google.api.services.vision.v1.VisionRequestInitializer

class AppInstance : Application() {

    companion object {
        var vision: Vision? = null
        fun getVisionInstance(): Vision {
            if (vision == null) {
                registerAPIVision()
            }
            return vision!!
        }

        private fun registerAPIVision() {
            val visionBuilder = Vision.Builder(NetHttpTransport(), AndroidJsonFactory(), null)
            visionBuilder.setApplicationName("com.tinhphan.visionperformance")
            visionBuilder.setVisionRequestInitializer(VisionRequestInitializer("AIzaSyB-5iJKcBuL5Nb0ka0UUeA_QvB3hrlDEws"))
            vision = visionBuilder.build()
        }
    }


}