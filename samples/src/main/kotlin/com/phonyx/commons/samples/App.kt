package com.phonyx.commons.samples

import com.github.ajalt.reprint.core.Reprint
import com.phonyx.commons.PhonyxApp

class App : PhonyxApp() {
    override fun onCreate() {
        super.onCreate()
        Reprint.initialize(this)
    }
}
