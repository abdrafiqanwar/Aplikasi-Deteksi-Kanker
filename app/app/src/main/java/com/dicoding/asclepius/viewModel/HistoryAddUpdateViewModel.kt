package com.dicoding.asclepius.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.database.History
import com.dicoding.asclepius.repository.HistoryRepository

class HistoryAddUpdateViewModel(application: Application) : ViewModel() {
    private val mHistoryRepository: HistoryRepository = HistoryRepository(application)

    fun insert(history: History) {
        mHistoryRepository.insert(history)
    }
}