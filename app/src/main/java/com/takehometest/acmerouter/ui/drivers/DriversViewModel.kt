package com.takehometest.acmerouter.ui.drivers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takehometest.acmerouter.entity.Driver
import com.takehometest.acmerouter.repo.driver.DriverRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriversViewModel @Inject constructor(
    private val driverRepo: DriverRepo
) : ViewModel() {
    private val _dataState: MutableLiveData<List<Driver>> = MutableLiveData()

    val dataState: LiveData<List<Driver>>
        get() = _dataState

    fun resetState() {
        viewModelScope.launch {
            _dataState.value = driverRepo.getDrivers(true)
        }
    }
}
