package com.example.myapplication.kotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by ldp.
 *
 * Date: 2021/10/21
 *
 * Summary:
 */
class KotlinXcViewModel : ViewModel() {

    val testLiveData = MutableLiveData<String>()

    fun requestData() {
        viewModelScope.launch {
            repeat(1) {
                delay(300)
                testLiveData.value = "viewModelScope_协程 ${System.currentTimeMillis()}"
            }
        }
    }
}