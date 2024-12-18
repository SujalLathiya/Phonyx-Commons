package com.phonyx.commons.dialogs

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.phonyx.commons.adapters.setupSimpleListItem
import com.phonyx.commons.databinding.ItemSimpleListBinding
import com.phonyx.commons.fragments.BaseBottomSheetDialogFragment
import com.phonyx.commons.models.SimpleListItem

open class BottomSheetChooserDialog : BaseBottomSheetDialogFragment() {

    var onItemClick: ((SimpleListItem) -> Unit)? = null

    override fun setupContentView(parent: ViewGroup) {
        val listItems = arguments?.getParcelableArray(ITEMS) as Array<SimpleListItem>
        listItems.forEach { item ->
            val view = ItemSimpleListBinding.inflate(layoutInflater, parent, false)
            setupSimpleListItem(view, item) {
                onItemClick?.invoke(it)
            }
            parent.addView(view.root)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onItemClick = null
    }

    companion object {
        private const val TAG = "BottomSheetChooserDialog"
        private const val ITEMS = "data"

        fun createChooser(
            fragmentManager: FragmentManager,
            title: Int?,
            items: Array<SimpleListItem>,
            callback: (SimpleListItem) -> Unit
        ): BottomSheetChooserDialog {
            val extras = Bundle().apply {
                if (title != null) {
                    putInt(BOTTOM_SHEET_TITLE, title)
                }
                putParcelableArray(ITEMS, items)
            }
            return BottomSheetChooserDialog().apply {
                arguments = extras
                onItemClick = callback
                show(fragmentManager, TAG)
            }
        }
    }
}
