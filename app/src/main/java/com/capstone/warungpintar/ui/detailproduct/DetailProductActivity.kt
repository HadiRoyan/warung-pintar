package com.capstone.warungpintar.ui.detailproduct

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.warungpintar.R

class DetailProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_product)
        window.statusBarColor = getColor(R.color.light_blue)
    }
}