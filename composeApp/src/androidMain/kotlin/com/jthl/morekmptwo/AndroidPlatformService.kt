package com.jthl.morekmptwo

import android.content.Context
import android.widget.Toast

/**
 * @author wanglei
 * @date 2025/12/5 9:19
 * @Description：
 */
class AndroidPlatformService(val applicationContext: Context) : PlatformService {
    override fun getDeviceId(): String {
        return ""
    }

    override fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}