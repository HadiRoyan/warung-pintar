package com.capstone.warungpintar.ui.addproduct

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.capstone.warungpintar.R
import com.capstone.warungpintar.databinding.ActivityAddProductInBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class AddProductInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductInBinding
    private val cameraRequest = 1888
    private lateinit var bitmap : Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddproductClose.setOnClickListener {
            onBackPressed()


        }
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraRequest
            )
        binding.kodestockEditTextLayout.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, cameraRequest)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == cameraRequest) {
            bitmap = data?.extras?.get("data") as Bitmap
            val image = InputImage.fromBitmap(bitmap, 0)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            recognizer.process(image)
                .addOnSuccessListener { text ->
                    processResultText(text)
                    Log.i("Text recognition: ", "Success")
                }
                .addOnFailureListener {
                    Log.i("Text recognition: ", "Failed")
                }
        }
    }
    private fun processResultText(resultText: Text) {
        if (resultText.textBlocks.size == 0) {
            return
        }
        for (blockText in resultText.textBlocks) {
            for (line in blockText.lines) {
                for (element in line.elements) {

                    // Create a mutable bitmap
                    val bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

                    // Get the block bounding box
                    // boundingBox = element.boundingBox
                    // canvas = Canvas(bitmap)
                    // paint.color = Color.RED
                    // paint.style = Paint.Style.STROKE
                    // paint.strokeWidth = 1F

                    // Draw the rectangle around the text recognized
                    // if (boundingBox != null) {
                       // canvas.drawRect(boundingBox!!, paint)
                    }
                }
                // receiptImage.setImageBitmap(bitmap)
            }
        }
    }
