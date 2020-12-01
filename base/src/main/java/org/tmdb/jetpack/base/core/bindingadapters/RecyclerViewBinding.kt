package org.tmdb.jetpack.base.core.bindingadapters

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.tmdb.jetpack.base.ui.adapter.AbstractAdapterItem
import org.tmdb.jetpack.base.ui.adapter.UniversalAdapter

@BindingAdapter("items", "comparator", "scrollDown", requireAll = false)
fun <T : AbstractAdapterItem> bindAdapterItems(
    recyclerView: RecyclerView,
    items: List<T>?,
    comparator: DiffUtil.ItemCallback<T>?,
    scrollDown: Boolean = false
) {
    items?.run {
        if (recyclerView.adapter == null) {
            val defaultComparator = object : DiffUtil.ItemCallback<T>() {
                override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = false

                override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = false
            }
            recyclerView.adapter = UniversalAdapter(comparator ?: defaultComparator)
        }
        (recyclerView.adapter as UniversalAdapter<T>).submitList(items)
        if (scrollDown) {
            (recyclerView.layoutManager as? LinearLayoutManager)
                ?.let { it.findLastCompletelyVisibleItemPosition() == recyclerView.adapter?.itemCount?.let { it - 1 } }
                ?: false
                    .takeIf { it }?.run {
                        recyclerView.scrollToPosition(items.size - 1)
                    }
        }
    }
}


@BindingAdapter("useDefaults")
fun useDefaults(recyclerView: RecyclerView, useDefault: Boolean) {
    if (!useDefault) return
    recyclerView.clipToPadding = false
    recyclerView.apply {
        itemAnimator = null
    }
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
}

@BindingAdapter("itemDecoration", "itemDecorationPaddingLeft", "itemDecorationPaddingRight", "addLastDivider", requireAll = false)
fun addItemDecoration(recyclerView: RecyclerView, divider: Drawable, paddingLeft: Float = 0f, paddingRight: Float = 0F, addLastDivider: Boolean = false) {
    recyclerView.addItemDecoration(DividerItemDecoration(divider, paddingLeft.toInt(), paddingRight.toInt(), addLastDivider))
}

@BindingAdapter("gridItemSpace")
fun addGridSpaceItemDecoration(recyclerView: RecyclerView, space: Float) {
    recyclerView.addItemDecoration(GridSpaceItemDecoration(space.toInt()))
}

@BindingAdapter("verticalItemSpace")
fun addVerticalSpaceItemDecoration(recyclerView: RecyclerView, space: Float) {
    recyclerView.addItemDecoration(VerticalSpaceItemDecoration(space.toInt()))
}

@BindingAdapter("bottomOffsetDecoration")
fun addBottomOffsetDecoration(recyclerView: RecyclerView, offset: Float) {
    recyclerView.addItemDecoration(BottomOffsetDecoration(offset.toInt()))
}

class BottomOffsetDecoration(
    private val bottomOffset: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemCount = state.itemCount
        val position = parent.getChildAdapterPosition(view)
        if (itemCount >0 && position == itemCount - 1) {
            outRect.set(0, 0, 0, bottomOffset)
        } else {
            outRect.set(0, 0, 0, 0)
        }
    }
}

class VerticalSpaceItemDecoration(
    private val space: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION
            || (itemPosition > 0 && view.visibility == View.GONE)) {
                return
        }
        val itemCount = state.itemCount
        if (itemPosition == 0) {
            outRect.top = space
        } else if (itemCount > 0 && itemPosition == itemCount) {
            outRect.top = 0
        } else {
            outRect.top = space
        }
    }
}

class GridSpaceItemDecoration(
    space: Int
) : RecyclerView.ItemDecoration() {
    private val halfSpace = space / 2

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.left = halfSpace
        outRect.right = halfSpace
        outRect.top = halfSpace
        outRect.bottom = halfSpace
    }
}

class DividerItemDecoration
constructor(
    private val divider: Drawable,
    private val paddingLeft: Int,
    private val paddingRight: Int,
    private val addLastDivider: Boolean
) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = paddingLeft.coerceAtLeast(parent.paddingLeft)
        val right = paddingRight.coerceAtLeast(parent.width - parent.paddingRight)
        val childCount = parent.childCount + if (addLastDivider) 1 else 0

        if (childCount > 1) {
            for (i in 0 until childCount - 1) {
                val child = parent.getChildAt(i)
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                val bottom = top + divider.intrinsicHeight

                divider.setBounds(left, top, right, bottom)
                divider.draw(c)
            }
        }
    }
}