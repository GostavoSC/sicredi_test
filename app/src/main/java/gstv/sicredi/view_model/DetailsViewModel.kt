package gstv.sicredi.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gstv.sicredi.core.utils.ResultWrapper
import gstv.sicredi.model.domain.Event
import gstv.sicredi.model.source.EventsRepository
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: EventsRepository) : ViewModel() {

    private val _eventDetails: MutableLiveData<Event> = MutableLiveData()
    val eventDetails: LiveData<Event> = _eventDetails

    private val _onCheckInDone: MutableLiveData<Unit> = MutableLiveData()
    val onCheckInDone: LiveData<Unit> = _onCheckInDone

    private val _onError: MutableLiveData<String?> = MutableLiveData()
    val onError: LiveData<String?> = _onError

    fun fetchDetails(eventId: String) {
        viewModelScope.launch {
            when (val result = repository.getEventDetails(eventId)) {
                is ResultWrapper.Success -> {
                    _eventDetails.postValue(result.value)
                }

                is ResultWrapper.GenericError -> {
                    _onError.postValue(result.error)
                }

                else -> {
                    _onError.postValue(null)
                }
            }
        }
    }

    fun sendCheckIn() {
        viewModelScope.launch {
            when (val result = repository.sendCheckIn()) {
                is ResultWrapper.Success -> {
                    _onCheckInDone.postValue(Unit)
                }

                is ResultWrapper.GenericError -> {
                    _onError.postValue(result.error)
                }

                else -> {
                    _onError.postValue(null)
                }
            }
        }
    }
}