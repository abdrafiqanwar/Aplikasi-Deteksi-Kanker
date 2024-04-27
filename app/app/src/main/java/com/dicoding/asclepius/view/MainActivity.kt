package com.dicoding.asclepius.view

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentImageUri: Uri? = null

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK){
            val data = it.data
            currentImageUri = data?.data
            showImage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener{
            startGallery()
        }
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        val pickImage = Intent(Intent.ACTION_PICK)
        pickImage.setType("image/*")
        resultLauncher.launch(pickImage)
    }

    private fun showImage() {
        // TODO: Menampilkan gambar sesuai Gallery yang dipilih.
        binding.previewImageView.setImageURI(currentImageUri)
    }

    private fun analyzeImage() {
        // TODO: Menganalisa gambar yang berhasil ditampilkan.
    }

    private fun moveToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}