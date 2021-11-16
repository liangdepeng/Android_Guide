package abc.common.util

import abc.common.util.KtExpandUtil.Companion.isOnMainThread
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.common_module.*

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
            return isOnMainThread
        }

        fun dp2px(dpValue: Float): Int {
            return dpToPx(dpValue)
        }

        fun sp2px(spValue: Float): Int {
            return spToPx(spValue)
        }

    }
}