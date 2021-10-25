package abc.common.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.common_module.simpleToast
import com.example.common_module.snackToast

/**
 * Created by ldp.
 *
 * Date: 2021/10/22
 *
 * Summary: Kotlin 扩展方法 提供给 Java调用
 */
class KtExpandUtil {

    companion object {

        val handler: Handler = Handler(Looper.getMainLooper())

        fun showToast(message: String?) {
            message?.simpleToast()
        }

        fun showSnackBar(context: Context?, message: String?) {
            message?.snackToast(context)
        }

        fun isOnMainThread(): Boolean {
            return Looper.myLooper() == Looper.getMainLooper()
        }


    }
}