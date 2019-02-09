package com.tinhphan.visionperformance.Services

import com.google.api.client.json.Json
import com.google.api.services.vision.v1.model.FaceAnnotation
import com.google.gson.JsonObject
import com.tinhphan.visionperformance.Model.Face
import com.tinhphan.visionperformance.Model.Faces
import org.json.JSONObject

class FacesDetectionJsonUtils() {
    companion object {
        fun asFacesJson(faceAnnotations: List<FaceAnnotation>): JsonObject {
            val numbersOfFace = faceAnnotations.size
            val faces = mutableListOf<Face>()
            for (i in 0..faceAnnotations.size - 1) {
                faces.add(
                    Face(
                        faceAnnotations[i].joyLikelihood,
                        faceAnnotations[i].sorrowLikelihood,
                        faceAnnotations[i].surpriseLikelihood,
                        faceAnnotations[i].angerLikelihood
                    )
                )
            }
            val localFaces: Faces = Faces(faces)
            val json = JsonObject()
            json.add("faces", localFaces.asJsonData())
            return json
        }
    }
}