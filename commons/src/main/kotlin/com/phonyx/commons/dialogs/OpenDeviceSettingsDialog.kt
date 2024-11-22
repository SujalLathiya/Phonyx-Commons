package com.phonyx.commons.dialogs

import com.phonyx.commons.R
import com.phonyx.commons.activities.BaseSimpleActivity
import com.phonyx.commons.databinding.DialogOpenDeviceSettingsBinding
import com.phonyx.commons.extensions.getAlertDialogBuilder
import com.phonyx.commons.extensions.openDeviceSettings
import com.phonyx.commons.extensions.setupDialogStuff

class OpenDeviceSettingsDialog(val activity: BaseSimpleActivity, message: String) {

    init {
        activity.apply {
            val view = DialogOpenDeviceSettingsBinding.inflate(layoutInflater, null, false)
            view.openDeviceSettings.text = message
            getAlertDialogBuilder()
                .setNegativeButton(R.string.close, null)
                .setPositiveButton(R.string.go_to_settings) { _, _ ->
                    openDeviceSettings()
                }.apply {
                    setupDialogStuff(view.root, this)
                }
        }
    }
}


