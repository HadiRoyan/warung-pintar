package com.capstone.warungpintar.ui.addproduct

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture


class AddScannerActivity : AppCompatActivity() {
    private var previewView: PreviewView? = null
    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.capstone.warungpintar.R.layout.activity_add_scanner)
        previewView = findViewById<PreviewView>(com.capstone.warungpintar.R.id.previewView)
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture!!.addListener({
            try {
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture!!.get()
                bindPreview(cameraProvider)
            } catch (e: Exception) {
                // Handle any errors
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder().build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
        val camera: Camera = cameraProvider.bindToLifecycle(
            this,
            cameraSelector,
            preview
        )
        preview.setSurfaceProvider(previewView!!.surfaceProvider)

        // Add border overlay
        val borderOverlay = BorderOverlay(this)
        (findViewById<View>(com.capstone.warungpintar.R.id.borderOverlay) as FrameLayout).addView(borderOverlay)
    }
}