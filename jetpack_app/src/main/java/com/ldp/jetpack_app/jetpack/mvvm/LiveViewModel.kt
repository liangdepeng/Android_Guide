package com.ldp.jetpack_app.jetpack.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * Created by ldp.
 *
 * Date: 2021/11/22
 *
 * Summary:
 */
class LiveViewModel : ViewModel() {

    val liveData: MutableLiveData<TestBean> = MutableLiveData()

    fun requestData() {
        viewModelScope.launch {
            delay(500)

            val modelData = TestBean().apply {
                data1 = "${Random().nextInt(1000)}  随机数据"
                data2 = "${Random().nextInt()}  随机数据"
                data3 = "${Random().nextInt()}  随机数据"
            }

            liveData.value = modelData
        }
    }

}