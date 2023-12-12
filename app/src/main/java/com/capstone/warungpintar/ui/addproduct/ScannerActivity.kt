package com.capstone.warungpintar.ui.addproduct

import android.Manifest
import android.R
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.SparseArray
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.CameraSource
import com.google.mlkit.vision.interfaces.Detector
import com.google.mlkit.vision.text.Text.TextBlock
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException


class ScannerActivity : AppCompatActivity() {
    private var mCameraSource: CameraSource? = null
    private var mTextRecognizer: TextRecognizerOptions? = null
    private var mSurfaceView: SurfaceView? = null
    private var mTextView: TextView? = null

    private val RC_HANDLE_CAMERA_PERM = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.capstone.warungpintar.R.layout.activity_scanner)
//        mSurfaceView = findViewById<View>(R.id.surfaceView) as SurfaceView
//        mTextView = findViewById<View>(R.id.textView) as TextView
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.CAMERA
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            // startTextRecognizer()
//        } else {
//            //askCameraPermission()
//        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        mCameraSource.release()
//    }

//    private fun startTextRecognizer() {
//        mTextRecognizer = TextRecognizerOptions.Builder().build()
//        if (!mTextRecognizer.isOperational()) {
//            Toast.makeText(
//                applicationContext,
//                "Oops ! Not able to start the text recognizer ...",
//                Toast.LENGTH_LONG
//            ).show()
//        } else {
//            mCameraSource = Builder(applicationContext, mTextRecognizer)
//                .setFacing(CameraSource.CAMERA_FACING_BACK)
//                .setRequestedPreviewSize(1280, 1024)
//                .setRequestedFps(15.0f)
//                .setAutoFocusEnabled(true)
//                .build()
//            mSurfaceView!!.holder.addCallback(object : SurfaceHolder.Callback {
//                override fun surfaceCreated(holder: SurfaceHolder) {
//                    if (ActivityCompat.checkSelfPermission(
//                            applicationContext,
//                            Manifest.permission.CAMERA
//                        ) == PackageManager.PERMISSION_GRANTED
//                    ) {
//                        try {
//                            mCameraSource.start(mSurfaceView!!.holder)
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//                    } else {
//                        askCameraPermission()
//                    }
//                }
//
//                override fun surfaceChanged(
//                    holder: SurfaceHolder,
//                    format: Int,
//                    width: Int,
//                    height: Int
//                ) {
//                }
//
//                override fun surfaceDestroyed(holder: SurfaceHolder) {
//                    mCameraSource.stop()
//                }
//            })
//            mTextRecognizer.setProcessor(object : Processor<TextBlock?>() {
//                fun release() {}
//                fun receiveDetections(detections: Detector.Detections<TextBlock?>) {
//                    val items: SparseArray<TextBlock> = detections.getDetectedItems()
//                    val stringBuilder = StringBuilder()
//                    for (i in 0 until items.size()) {
//                        val item = items.valueAt(i)
//                        if (item != null && item.getValue() != null) {
//                            stringBuilder.append(item.getValue() + " ")
//                        }
//                    }
//                    val fullText = stringBuilder.toString()
//                    val handler = Handler(Looper.getMainLooper())
//                    handler.post(Runnable { mTextView!!.text = fullText })
//                }
//            })
//        }
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>,
//        grantResults: IntArray
//    ) {
//        if (requestCode != RC_HANDLE_CAMERA_PERM) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//            return
//        }
//        if (grantResults.size != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            startTextRecognizer()
//            return
//        }
//    }
//
//    private fun askCameraPermission() {
//        val permissions = arrayOf<String>(Manifest.permission.CAMERA)
//        if (!ActivityCompat.shouldShowRequestPermissionRationale(
//                this,
//                Manifest.permission.CAMERA
//            )
//        ) {
//            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM)
//            return
//        }
//    }
}