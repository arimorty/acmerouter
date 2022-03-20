package com.takehometest.acmerouter.ui.destination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takehometest.acmerouter.entity.Destination
import com.takehometest.acmerouter.entity.Driver
import com.takehometest.acmerouter.usecase.GetDestinationForDriver
import com.takehometest.acmerouter.usecase.GetDriverById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DestinationViewState(
    var driver: Driver? = null,
    var destination: Destination? = null,
)

@HiltViewModel
class DestinationViewModel @Inject constructor(
    private val getDestinationForDriver: GetDestinationForDriver,
    private val getDriverById: GetDriverById
) : ViewModel() {
    private val _dataState: MutableLiveData<DestinationViewState> = MutableLiveData()

    val dataState: LiveData<DestinationViewState>
        get() = _dataState

    fun resetState(driverId: String) {
        viewModelScope.launch {
            val state = DestinationViewState();
            state.driver = getDriverById.execute(driverId)
            state.destination = getDestinationForDriver.execute(driverId)

            _dataState.value = state
        }
    }
}
