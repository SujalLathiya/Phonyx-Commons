package com.phonyx.commons

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.phonyx.commons.extensions.appLockManager
import com.phonyx.commons.extensions.checkUseEnglish

open class PhonyxApp : Application() {

    open val isAppLockFeatureAvailable = false

    override fun onCreate() {
        super.onCreate()
        checkUseEnglish()
        setupAppLockManager()
    }

    private fun setupAppLockManager() {
        if (isAppLockFeatureAvailable) {
            ProcessLifecycleOwner.get().lifecycle.addObserver(appLockManager)
        }
    }
}
