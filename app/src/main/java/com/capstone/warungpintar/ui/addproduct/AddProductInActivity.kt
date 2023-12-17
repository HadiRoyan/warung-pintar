package com.capstone.warungpintar.ui.addproduct

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.capstone.warungpintar.R
import com.capstone.warungpintar.databinding.ActivityAddProductInBinding
import com.capstone.warungpintar.databinding.DialogResultScannerLayoutBinding
import com.capstone.warungpintar.ui.addproduct.AddScannerActivity.Companion.CAMERAX_RESULT
import com.capstone.warungpintar.utils.Validation
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddProductInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductInBinding
    private val cameraRequest = 1888
    private var currentImageUri: Uri? = null
    private var expiredDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddproductClose.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnAddGambar.setOnClickListener {
            checkCameraPermissionForImage()
        }


        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraRequest
            )
        }

        binding.kodestockEditTextLayout.setOnClickListener {
            startCameraXForScanning()
            showDialog()
        }
    }

    private fun checkCameraPermissionForImage() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCameraForImage()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                cameraRequest
            )
        }
    }

    private fun openCameraForImage() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            launcherTakePicture.launch(takePictureIntent)
        }
    }

    private val launcherTakePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                imageBitmap?.let {
                    currentImageUri = saveImageAndGetUri(it)
                    binding.ivProductImage.setImageURI(currentImageUri)
                }
            }
        }

    private fun saveImageAndGetUri(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val path = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "IMG_$timeStamp",
            null
        )
        return Uri.parse(path)
    }

    private fun showDialog() {
        val alertDialog = MaterialAlertDialogBuilder(this)
        alertDialog.setTitle("Informasi!")
            .setMessage(getString(R.string.ocr_information_dialog))
            .setPositiveButton("Mengerti dan Lanjutkan") { _, _ ->
                startCameraXForScanning()
            }
        alertDialog.create().show()
    }

    private fun startCameraXForScanning() {
        val intent = Intent(this, AddScannerActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri =
                it.data?.getStringExtra(AddScannerActivity.EXTRA_CAMERAX_IMAGE)?.toUri()

            if (currentImageUri != null) {
                binding.ivProductImage.setImageURI(currentImageUri)
                showDialogResult(currentImageUri!!)
                Log.d(TAG, "image uri: ${currentImageUri.toString()}")
            } else {
                Toast.makeText(
                    this,
                    "Gagal mengambil atau menampilkan gambar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showDialogResult(uri: Uri) {
        val binding = DialogResultScannerLayoutBinding.inflate(layoutInflater)
        val buttonScan = binding.btnScanOcr
        val buttonResult = binding.btnSave

        val alertDialog = MaterialAlertDialogBuilder(this)
            .setView(binding.root)
            .create()

        binding.ivResultScan.setImageURI(uri)

        buttonScan.setOnClickListener {
            // TODO: UnImplemented service
            Toast.makeText(
                this,
                "Scanning [TESTING - UNIMPLEMENTED]",
                Toast.LENGTH_SHORT
            ).show()
        }

        buttonResult.setOnClickListener {
            val isExpiredDateValid = Validation.validateIsNotEmpty(
                "Tanggal Kadaluarsa",
                binding.layoutExpiredDate,
                binding.etExpiredDate
            )

            if (isExpiredDateValid) {
                expiredDate = binding.etExpiredDate.toString().trim()
                alertDialog.dismiss()
            } else {
                Toast.makeText(this, "Masukan tanggal kadaluarsa", Toast.LENGTH_SHORT).show()
            }
        }

        alertDialog.show()
    }

    companion object {
        private const val TAG = "AddProductInActivity"
    }
}