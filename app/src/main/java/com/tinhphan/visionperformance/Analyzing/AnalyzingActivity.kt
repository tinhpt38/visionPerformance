package com.tinhphan.visionperformance.Analyzing

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonObject
import com.tinhphan.visionperformance.AnalyzerResult.AnalyzerResultActivity
import com.tinhphan.visionperformance.Model.ImageAnaly
import com.tinhphan.visionperformance.R
import com.tinhphan.visionperformance.Services.VisionService
import com.tinhphan.visionperformance.Utils.CommunityKey

class AnalyzingActivity : AppCompatActivity() {

    //region SYSTEM EVENTS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyzing)
        receiveData()
        callService()
        mapView()
        onCancelClick()
    }

    //endregion

    //region UTILS
    private fun mapView() {
        actionViewCancel = findViewById(R.id.action_cancel)
        displayViewAnalyzing = findViewById(R.id.progress_bar_analyzing)
        displayViewCurrentProgress = findViewById(R.id.display_current_progress)
    }

    private fun receiveData() {
        val intent = getIntent()
        val bundle = intent.getBundleExtra(CommunityKey.OPEN_ANALYZING)
        imageAnaly = ImageAnaly.asImageAnaly(bundle)
    }

    private fun callService() {
        val visionService = VisionService(this, imageAnaly)
        visionService.resultCallBack = {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
        visionService.onProgressValueCallBack ={
            displayViewCurrentProgress.text = String.format("%d %",it)
            displayViewAnalyzing.progress = it
        }
        visionService.execute()

    }

    private fun openAnalyserResult(data: JsonObject){
        val intent = Intent(this,AnalyzerResultActivity::class.java)
        val bundle = Bundle()
        //todo: create object result
    }
    //endregion

    //region VIEW EVENTS
    private fun onCancelClick() {
        actionViewCancel.setOnClickListener {
            finish()
        }
    }
    //endregion


    //region VARS
    private lateinit var actionViewCancel: Button
    private lateinit var displayViewAnalyzing: ProgressBar
    private lateinit var displayViewCurrentProgress: TextView

    private lateinit var imageAnaly: ImageAnaly
    //endregion
}
