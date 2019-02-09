package com.tinhphan.visionperformance.Cameraman

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.widget.Button
import android.widget.ImageView
import com.tinhphan.visionperformance.Analyzing.AnalyzingActivity
import com.tinhphan.visionperformance.Model.PhotoContentType
import com.tinhphan.visionperformance.Model.ImageAnaly
import com.tinhphan.visionperformance.R
import com.tinhphan.visionperformance.Utils.BitmapUtils
import com.tinhphan.visionperformance.Utils.CommunityKey

class CameramanActivity : AppCompatActivity() {

    //region VARS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cameraman)
        mapView()
        onTakeAPhotoClick()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == codeRequestOpenCamera) {
            data?.extras?.get("data")?.let {
                resultViewTakeAPhoto.setImageBitmap(it as Bitmap)
                openAnalyzing(it as Bitmap)
            }
        }
    }

    //endregion

    //region UTILS
    private fun mapView() {
        actionTakePhotoFace = findViewById(R.id.action_take_face)
        actionTakePhotoOther = findViewById(R.id.action_take_label)
        resultViewTakeAPhoto = findViewById(R.id.result_take_a_photo)
    }

    private fun onTakeAPhotoClick() {
        actionTakePhotoFace.setOnClickListener {
            photoPhotoContentType = PhotoContentType.FACE_DETECTION
            openCamera()
        }

        actionTakePhotoOther.setOnClickListener {
            photoPhotoContentType = PhotoContentType.LABEL_DETECTION
            openCamera()
        }
    }

    private fun openCamera() {
        registerCameraPermission()
        val cameraAction = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraAction, codeRequestOpenCamera)
    }

    @SuppressLint("NewApi")
    private fun registerCameraPermission() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 0)
        }
    }

    private fun openAnalyzing(bitmap: Bitmap) {
        val intent = Intent(this, AnalyzingActivity::class.java)
        val bundle = Bundle()
        bundle.putByteArray(CommunityKey.OPEN_ANALYZING, BitmapUtils.bitmapToByteArray(bitmap))
        val imageAnaly = ImageAnaly(photoPhotoContentType,BitmapUtils.bitmapToByteArray(bitmap))
        intent.putExtra(CommunityKey.OPEN_ANALYZING,imageAnaly.asBundleParams())
        startActivity(intent)
    }


    //endregion


    //region VARS
    private lateinit var actionTakePhotoFace: Button
    private lateinit var actionTakePhotoOther: Button
    private lateinit var resultViewTakeAPhoto: ImageView
    private lateinit var photoPhotoContentType: PhotoContentType

    private val codeRequestOpenCamera: Int = 309

    //endregion
}
