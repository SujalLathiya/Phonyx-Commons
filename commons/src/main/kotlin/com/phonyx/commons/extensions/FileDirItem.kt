package com.phonyx.commons.extensions

import android.content.Context
import com.phonyx.commons.models.FileDirItem

fun FileDirItem.isRecycleBinPath(context: Context): Boolean {
    return path.startsWith(context.recycleBinPath)
}
