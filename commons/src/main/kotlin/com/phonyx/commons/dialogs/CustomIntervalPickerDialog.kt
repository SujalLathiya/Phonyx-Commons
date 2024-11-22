package com.phonyx.commons.dialogs

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.collections.immutable.toImmutableList
import com.phonyx.commons.R
import com.phonyx.commons.databinding.DialogCustomIntervalPickerBinding
import com.phonyx.commons.extensions.*
import com.phonyx.commons.helpers.DAY_SECONDS
import com.phonyx.commons.helpers.HOUR_SECONDS
import com.phonyx.commons.helpers.MINUTE_SECONDS

class CustomIntervalPickerDialog(val activity: Activity, val selectedSeconds: Int = 0, val showSeconds: Boolean = false, val callback: (minutes: Int) -> Unit) {
    private var dialog: AlertDialog? = null
    private var view = DialogCustomIntervalPickerBinding.inflate(activity.layoutInflater, null, false)

    init {
        activity.getAlertDialogBuilder()
            .setPositiveButton(R.string.ok) { _, _ -> confirmReminder() }
            .setNegativeButton(R.string.cancel, null)
            .apply {
                activity.setupDialogStuff(view.root, this) { alertDialog ->
                    dialog = alertDialog
                    alertDialog.showKeyboard(view.dialogCustomIntervalValue)
                }
            }

        view.apply {
            dialogRadioSeconds.beVisibleIf(showSeconds)
            when {
                selectedSeconds == 0 -> dialogRadioView.check(R.id.dialog_radio_minutes)
                selectedSeconds % DAY_SECONDS == 0 -> {
                    dialogRadioView.check(R.id.dialog_radio_days)
                    dialogCustomIntervalValue.setText((selectedSeconds / DAY_SECONDS).toString())
                }

                selectedSeconds % HOUR_SECONDS == 0 -> {
                    dialogRadioView.check(R.id.dialog_radio_hours)
                    dialogCustomIntervalValue.setText((selectedSeconds / HOUR_SECONDS).toString())
                }

                selectedSeconds % MINUTE_SECONDS == 0 -> {
                    dialogRadioView.check(R.id.dialog_radio_minutes)
                    dialogCustomIntervalValue.setText((selectedSeconds / MINUTE_SECONDS).toString())
                }

                else -> {
                    dialogRadioView.check(R.id.dialog_radio_seconds)
                    dialogCustomIntervalValue.setText(selectedSeconds.toString())
                }
            }

            dialogCustomIntervalValue.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        dialog?.getButton(DialogInterface.BUTTON_POSITIVE)?.performClick()
                        return true
                    }

                    return false
                }
            })
        }
    }

    private fun confirmReminder() {
        val value = view.dialogCustomIntervalValue.value
        val multiplier = getMultiplier(view.dialogRadioView.checkedRadioButtonId)
        val minutes = Integer.valueOf(value.ifEmpty { "0" })
        callback(minutes * multiplier)
        activity.hideKeyboard()
        dialog?.dismiss()
    }

    private fun getMultiplier(id: Int) = when (id) {
        R.id.dialog_radio_days -> DAY_SECONDS
        R.id.dialog_radio_hours -> HOUR_SECONDS
        R.id.dialog_radio_minutes -> MINUTE_SECONDS
        else -> 1
    }
}


private fun initialSelection(selectedSeconds: Int, context: Context) = requireNotNull(
    when {
        selectedSeconds == 0 -> minutesRaw(context)
        selectedSeconds % DAY_SECONDS == 0 -> daysRaw(context)
        selectedSeconds % HOUR_SECONDS == 0 -> hoursRaw(context)
        selectedSeconds % MINUTE_SECONDS == 0 -> minutesRaw(context)
        else -> secondsRaw(context)

    }
) {
    "Incorrect format, please check selections"
}



fun buildCustomIntervalEntries(context: Context, showSeconds: Boolean) =
    buildList {
        if (showSeconds) {
            add(secondsRaw(context))
        }
        add(minutesRaw(context))
        add(hoursRaw(context))
        add(daysRaw(context))
    }.toImmutableList()

private fun daysRaw(context: Context) = context.getString(R.string.days_raw)
private fun hoursRaw(context: Context) = context.getString(R.string.hours_raw)
private fun secondsRaw(context: Context) = context.getString(R.string.seconds_raw)
private fun minutesRaw(context: Context) = context.getString(R.string.minutes_raw)

private fun getMultiplier(context: Context, text: String) = when (text) {
    daysRaw(context) -> DAY_SECONDS
    hoursRaw(context) -> HOUR_SECONDS
    minutesRaw(context) -> MINUTE_SECONDS
    else -> 1
}

