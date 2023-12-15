package com.capstone.warungpintar.ui.addproduct

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.capstone.warungpintar.databinding.ActivityAddProductInBinding
import com.capstone.warungpintar.ui.addproduct.AddScannerActivity.Companion.CAMERAX_RESULT

class AddProductInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductInBinding
    private val cameraRequest = 1888

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddproductClose.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
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
            startCameraX()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, AddScannerActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri =
                it.data?.getStringExtra(AddScannerActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            Log.d(TAG, "image uri: ${currentImageUri.toString()}")
        }
    }

    companion object {
        private const val TAG = "AddProductInActivity"
    }
}
