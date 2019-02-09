package com.tinhphan.visionperformance.Model

import android.media.Image
import android.os.Bundle
import org.json.JSONObject

class ImageAnaly(var typeDirectionPhoto: PhotoContentType, val dataPhoto: ByteArray) {

    companion object {
        fun asImageAnaly(bundle: Bundle): ImageAnaly {
            val typeDetection = bundle.getString("typeDetection")
            val dataPhoto = bundle.getByteArray("userImageData")
            val photoContentType = PhotoContentType.valueOf(typeDetection)
            return ImageAnaly(photoContentType, dataPhoto)

        }
    }

    fun asBundleParams(): Bundle {
        val bundle = Bundle()
        bundle.putString("typeDetection", typeDirectionPhoto.toString())
        bundle.putByteArray("userImageData", dataPhoto)
        return bundle
    }
}


enum class PhotoContentType {
    FACE_DETECTION, LABEL_DETECTION, UN_KNOW
}