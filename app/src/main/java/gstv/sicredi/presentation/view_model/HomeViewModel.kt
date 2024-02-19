package gstv.sicredi.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gstv.sicredi.core.utils.ResultWrapper
import gstv.sicredi.source.EventsRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: EventsRepository) : ViewModel() {

    init {
        getAllBreeds()
    }

    private fun getAllBreeds() {
        viewModelScope.launch {
            when (val result = repository.getAllEvents()) {
                is ResultWrapper.Success -> {

                }

                else -> {

                }
            }
        }
    }
}