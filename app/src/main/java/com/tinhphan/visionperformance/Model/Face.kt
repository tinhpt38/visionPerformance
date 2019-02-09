package com.tinhphan.visionperformance.Model

import com.google.gson.JsonObject

class Face(
    var joyLikelihood: String,
    var sorrowLikelihood: String,
    var surpriseLikelihood: String,
    var angerLikelihood: String
) {

    private val VERY_LIKELY = "VERY_LIKELY"
    private val VERY_UNLIKELY = "VERY_UNLIKELY"
    fun getVeryLike(): String {
        val resultVeryLikeBuffer = StringBuffer()
        if (joyLikelihood == VERY_LIKELY) resultVeryLikeBuffer.append("HAPPY")
        if (angerLikelihood == VERY_LIKELY) resultVeryLikeBuffer.append("ANGER")
        if (sorrowLikelihood == VERY_LIKELY) resultVeryLikeBuffer.append("SORROW")
        if (surpriseLikelihood == VERY_LIKELY) resultVeryLikeBuffer.append("SURPRISE")
        return resultVeryLikeBuffer.toString()
    }

    fun asJsonData():JsonObject{
        val json = JsonObject()
        json.addProperty("state",getVeryLike())
        return json
    }

}

class Faces(var faces: List<Face>){

    fun asJsonData():JsonObject{
        val json= JsonObject()
        json.addProperty("numberOfFaces",faces.size)
        for (i in 0..faces.size-1){
            json.add("No $i",faces[i].asJsonData())
        }
        return  json
    }

}