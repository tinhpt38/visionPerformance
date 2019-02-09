package com.tinhphan.visionperformance.Analyzing

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonObject
import com.tinhphan.visionperformance.AnalyzerResult.FacesResultActivity
import com.tinhphan.visionperformance.Model.ImageAnaly
import com.tinhphan.visionperformance.R
import com.tinhphan.visionperformance.Services.VisionService
import com.tinhphan.visionperformance.Utils.CommunityKeyUtils

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
        actBtnCancel = findViewById(R.id.act_button_cancel)
        disProBarAnalyzing = findViewById(R.id.dis_progress_analyzing)
        disTvProgressValue = findViewById(R.id.dis_text_progress_value)
    }

    private fun receiveData() {
        val intent = getIntent()
        val bundle = intent.getBundleExtra(CommunityKeyUtils.OPEN_ANALYZING)
        imageAnaly = ImageAnaly.asImageAnaly(bundle)
    }

    private fun callService() {
        val visionService = VisionService(imageAnaly)
        visionService.resultCallBack = {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
        visionService.onProgressValueCallBack ={
            disTvProgressValue.text = String.format("%d %",it)
            disProBarAnalyzing.progress = it
        }
        visionService.execute()

    }

    private fun openFacesResultActivity(data: JsonObject){
        val intent = Intent(this,FacesResultActivity::class.java)
        val bundle = Bundle()
        //todo: create object result???
    }

    private fun openLabelResultActivity(data: JsonObject){
        val intent = Intent(this,FacesResultActivity::class.java)

    }
    //endregion

    //region VIEW EVENTS
    private fun onCancelClick() {
        actBtnCancel.setOnClickListener {
            finish()
        }
    }
    //endregion


    //region VARS
    private lateinit var actBtnCancel: Button
    private lateinit var disProBarAnalyzing: ProgressBar
    private lateinit var disTvProgressValue: TextView

    private lateinit var imageAnaly: ImageAnaly
    //endregion
}
