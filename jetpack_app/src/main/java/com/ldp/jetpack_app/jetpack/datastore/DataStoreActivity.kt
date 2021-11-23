package com.ldp.jetpack_app.jetpack.datastore

import android.os.Bundle
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.ldp.jetpack_app.databinding.ActivityDataStoreBinding
import com.ldp.jetpack_app.jetpack.base.BaseMvvmActivity
import com.ldp.jetpack_app.jetpack.base.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DataStoreActivity : BaseMvvmActivity<ActivityDataStoreBinding,BaseViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding.saveSyncBtn.setOnClickListener {
            runBlocking {
                // 异步获取 协程  runBlocking 同步保存
                DataStoreUtil.asyncSave(stringPreferencesKey("testKey1"),"同步保存成功 :"+mViewBinding.et.text.toString())
                Toast.makeText(this@DataStoreActivity,"同步保存成功",Toast.LENGTH_SHORT).show()
            }
        }

        /**
         *--------- 推荐 异步保存  ---------
         */
        mViewBinding.saveAsyncBtn.setOnClickListener {
            lifecycleScope.launch {
                // 此种方法 协程异步 保存
                DataStoreUtil.asyncSave(stringPreferencesKey("testKey2"),"异步保存成功 :"+mViewBinding.et.text.toString())
                Toast.makeText(this@DataStoreActivity,"异步保存成功",Toast.LENGTH_SHORT).show()
            }
        }

        mViewBinding.getSyncBtn.setOnClickListener {
            var asyncGetData :String?=""
            // 阻塞式 携程调用 阻塞调用线程 异步携程变成同步调用 直到携程执行完毕
            runBlocking {
                asyncGetData = DataStoreUtil.asyncGet(stringPreferencesKey("testKey1"))
            }
            // 上面的是阻塞的 所以 能获取到
            mViewBinding.resultTv.text=asyncGetData
        }

        /**
         *--------- 推荐 异步获取  ---------
         */
        mViewBinding.getAsyncBtn.setOnClickListener {
            var asyncGetData :String?=""
            // 携程里面的代码 suspend 标记的方法函数 会阻塞携程 但是不会阻塞线程
            lifecycleScope.launch {
                // suspend 标记 会阻塞挂起携程等待 执行完毕
                asyncGetData = DataStoreUtil.asyncGet(stringPreferencesKey("testKey2"))

                mViewBinding.resultTv.text=asyncGetData
            }
          // 上面协程 是异步执行的 就意味着 如果在下面这句话获取数据 获取不到
          // mViewBinding.resultTv.text=asyncGetData
        }

    }
}