package com.tinhphan.visionperformance.Services

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import org.apache.commons.io.IOUtils
import org.json.JSONObject
import java.io.InputStream
import java.net.URL
import android.graphics.Bitmap.CompressFormat
import android.R.attr.bitmap
import com.google.api.services.vision.v1.model.AnnotateImageRequest
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest
import com.google.api.services.vision.v1.model.Feature
import com.google.api.services.vision.v1.model.Image
import com.google.gson.JsonObject
import com.tinhphan.visionperformance.Analyzing.AnalyzingActivity
import com.tinhphan.visionperformance.AppInstance
import com.tinhphan.visionperformance.Model.ImageAnaly
import com.tinhphan.visionperformance.Model.PhotoContentType
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class VisionService(val context: Context, val imageAnaly: ImageAnaly) : AsyncTask<Unit, Int, JsonObject>() {

    override fun doInBackground(vararg params: Unit?): JsonObject {

        val inputImage = Image()
        inputImage.encodeContent(imageAnaly.dataPhoto)
        val feature = Feature()
        feature.setType(imageAnaly.typeDirectionPhoto.toString())

        val annoImageRequest = AnnotateImageRequest()
        annoImageRequest.setImage(inputImage)
        annoImageRequest.setFeatures(listOf(feature))

        val batchRequest = BatchAnnotateImagesRequest()
        batchRequest.setRequests(listOf(annoImageRequest))

        val batchResponse = AppInstance.getVisionInstance().images().annotate(batchRequest).execute()

        //if face
        val faces = batchResponse.responses.get(0).faceAnnotations

        // if label
        //todo: update for label

        val jsonObject: JsonObject = when (imageAnaly.typeDirectionPhoto) {
            PhotoContentType.FACE_DETECTION -> FacesDetectionJsonUtils.asFacesJson(faces)
            //todo: update for label
//            PhotoContentType.LABEL_DETECTION->labelDetection()
            else -> {
                JsonObject()
            }
        }
        return jsonObject
    }

    override fun onPostExecute(result: JsonObject?) {
        super.onPostExecute(result)
        result?.let {
            resultCallBack?.invoke(it)
        }
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        values[0]?.let {
            onProgressValueCallBack?.invoke(it)
        }
    }


    var onProgressValueCallBack: ((Int) -> Unit)? = null
    var resultCallBack:((JsonObject)->Unit)? = null


}