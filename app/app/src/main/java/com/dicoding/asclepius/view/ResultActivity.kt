package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dicoding.asclepius.database.History
import com.dicoding.asclepius.database.HistoryRoomDatabase
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.viewModel.HistoryViewModel
import com.dicoding.asclepius.viewModel.ViewModelFactory
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    private val historyViewModel by viewModels<HistoryViewModel>() {
        ViewModelFactory.getInstance(application)
    }
    private var history: History? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        val image = intent.getStringExtra(EXTRA_IMAGE_URI)
        val imageUri = Uri.parse(image)
        binding.resultImage.setImageURI(imageUri)

        var displayResult = ""

        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener{
                override fun onError(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                    runOnUiThread {
                        results?.let { it ->
                            if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                println(it)
                                val sortedCategories =
                                    it[0].categories.sortedByDescending { it?.score }
                                displayResult =
                                    sortedCategories.joinToString("\n") {
                                        "${it.label} " + NumberFormat.getPercentInstance()
                                            .format(it.score).trim()
                                    }
                                binding.resultText.text = displayResult
                            } else {
                                binding.resultText.text = ""
                            }
                        }
                    }
                }
            }
        )
        imageClassifierHelper.classifyStaticImage(imageUri)

        if(history == null){
            history = History()
        }

        history.let {
            it?.image = imageUri.toString()
            it?.result = displayResult
        }

        historyViewModel.insert(history as History)

//        val database = HistoryRoomDatabase.getDatabase(this)
//        database.historyDao().insert(history as History)
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}