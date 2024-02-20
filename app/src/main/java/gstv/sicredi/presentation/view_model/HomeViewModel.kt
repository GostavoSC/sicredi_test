package gstv.sicredi.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gstv.sicredi.core.utils.ResultWrapper
import gstv.sicredi.domain.Event
import gstv.sicredi.source.EventsRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: EventsRepository) : ViewModel() {


    private val _eventsList: MutableLiveData<List<Event>> = MutableLiveData()
    val eventsList: LiveData<List<Event>> = _eventsList


    fun getAllEvents() {
        viewModelScope.launch {
            when (val result = repository.getAllEvents()) {
                is ResultWrapper.Success -> {
                    _eventsList.postValue(result.value)
                }

                else -> {

                }
            }
        }
    }
}