package com.phonyx.commons.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import com.phonyx.commons.R
import com.phonyx.commons.helpers.DARK_GREY
import com.phonyx.commons.helpers.isSPlus
import com.phonyx.commons.views.*

fun Context.isDynamicTheme() = isSPlus() && baseConfig.isSystemThemeEnabled

fun Context.isBlackAndWhiteTheme() = baseConfig.textColor == Color.WHITE && baseConfig.primaryColor == Color.BLACK && baseConfig.backgroundColor == Color.BLACK

fun Context.isWhiteTheme() = baseConfig.textColor == DARK_GREY && baseConfig.primaryColor == Color.WHITE && baseConfig.backgroundColor == Color.WHITE

fun Context.isAutoTheme() = !isSPlus() && baseConfig.isSystemThemeEnabled

fun Context.getProperTextColor() = resources.getColor(R.color.you_neutral_text_color, theme)

fun Context.getProperBackgroundColor() = resources.getColor(R.color.you_background_color, theme)


fun Context.getProperPrimaryColor() = resources.getColor(R.color.you_primary_color, theme)

fun Context.getProperStatusBarColor() = resources.getColor(R.color.you_status_bar_color, theme)

// get the color of the status bar with material activity, if the layout is scrolled down a bit
fun Context.getColoredMaterialStatusBarColor(): Int {
    return resources.getColor(R.color.you_status_bar_color, theme)
}

fun Context.updateTextColors(viewGroup: ViewGroup) {
    val textColor =  getProperTextColor()

    val backgroundColor = baseConfig.backgroundColor
    val accentColor = when {
        isWhiteTheme() || isBlackAndWhiteTheme() -> baseConfig.accentColor
        else -> getProperPrimaryColor()
    }

    val cnt = viewGroup.childCount
    (0 until cnt).map { viewGroup.getChildAt(it) }.forEach {
        when (it) {
            is MyTextView -> it.setColors(textColor, accentColor, backgroundColor)
            is MyAppCompatSpinner -> it.setColors(textColor, accentColor, backgroundColor)
            is MyCompatRadioButton -> it.setColors(textColor, accentColor, backgroundColor)
            is MyAppCompatCheckbox -> it.setColors(textColor, accentColor, backgroundColor)
            is MyMaterialSwitch -> it.setColors(textColor, accentColor, backgroundColor)
            is MyEditText -> it.setColors(textColor, accentColor, backgroundColor)
            is MyAutoCompleteTextView -> it.setColors(textColor, accentColor, backgroundColor)
            is MyFloatingActionButton -> it.setColors(textColor, accentColor, backgroundColor)
            is MySeekBar -> it.setColors(textColor, accentColor, backgroundColor)
            is MyButton -> it.setColors(textColor, accentColor, backgroundColor)
            is MyTextInputLayout -> it.setColors(textColor, accentColor, backgroundColor)
            is ViewGroup -> updateTextColors(it)
        }
    }
}

fun Context.getTimePickerDialogTheme() = R.style.MyTimePickerMaterialTheme_Dark

fun Context.getDatePickerDialogTheme() = R.style.MyDateTimePickerMaterialTheme

fun Context.getPopupMenuTheme(): Int {
    return R.style.AppTheme_YouPopupMenuStyle
}

@SuppressLint("NewApi")
fun Context.getBottomNavigationBackgroundColor(): Int {
    val bottomColor =  resources.getColor(R.color.you_status_bar_color, theme)
    return bottomColor
}
