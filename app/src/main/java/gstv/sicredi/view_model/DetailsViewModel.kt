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

    private val _onError: MutableLiveData<ResultWrapper<*>> = MutableLiveData()
    val onError: LiveData<ResultWrapper<*>> = _onError

    private var eventId: String = ""

    fun fetchDetails(eventId: String) {
        this.eventId = eventId
        viewModelScope.launch {
            when (val result = repository.getEventDetails(eventId)) {
                is ResultWrapper.Success -> {
                    _eventDetails.postValue(result.value)
                }

                else -> {
                    _onError.postValue(result)
                }
            }
        }
    }

    fun sendCheckIn() {
        viewModelScope.launch {
            if (eventId.isEmpty()) {
                _onError.postValue(ResultWrapper.GenericError())
                return@launch
            }

            when (val result = repository.sendCheckIn(eventId)) {
                is ResultWrapper.Success -> {
                    _onCheckInDone.postValue(Unit)
                }

                else -> {
                    _onError.postValue(result)
                }
            }
        }
    }
}