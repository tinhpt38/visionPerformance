package com.tinhphan.visionperformance.Utils

import android.graphics.Bitmap
import org.apache.commons.io.IOUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class BitmapUtils {
    companion object {
        fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos)
            val bitmapdata = bos.toByteArray()
            val bs = ByteArrayInputStream(bitmapdata)
            val photoData = IOUtils.toByteArray(bs)
            bs.close()
            return photoData
        }
    }
}