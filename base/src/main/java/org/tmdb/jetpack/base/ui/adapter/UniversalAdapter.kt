package org.tmdb.jetpack.base.ui.adapter

import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference

/**
 * Now you do not need to write an adapter every time.
 * Use this universal adapter that uses binding underhood.
 *
 * Steps how to use:
 * - Create adapter item inherited from AbstractAdapterItem
 *   override ```fun layoutId()``` and add MutableLiveData properties to bind into item's layout
 * - In item's layout in <data> section declare variable with name "item" with type of created adapter item
 *   Example
 *   ```
 *       <data>
 *        <variable
 *          name="item"
 *          type="xxx.xxx.YourAdapterItem" />
 *       </data>
 *   ```
 *   - In view model create ObservableList<AbstractAdapterItem> property with adapter items
 *   - Set this items to RecyclerView using binding
 *   ```
 *   <androidx.recyclerview.widget.RecyclerView
 *   app:items="@{viewModel.transactionItems}"
 *   app:comparator="@{YourComparator}>
 *   ```
 * */
class UniversalAdapter<T : AbstractAdapterItem>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, UniversalViewHolder>(diffCallback) {

    private val observableListCallback = ObservableListCallback(this)
    private var recyclerView: RecyclerView? = null
    private lateinit var inflater: LayoutInflater
    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniversalViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return UniversalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UniversalViewHolder, position: Int) {
        val item = getItem(position)
        val isSelected = tracker?.isSelected(position.toLong()) ?: false
        holder.bind(item, isSelected)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        if (currentList is ObservableList) {
            (currentList as ObservableList<T>).addOnListChangedCallback(observableListCallback)
        }
        this.recyclerView = recyclerView
        inflater = LayoutInflater.from(recyclerView.context)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        if (recyclerView != null && currentList is ObservableList) {
            (currentList as ObservableList<T>).removeOnListChangedCallback(observableListCallback)
        }
        this.recyclerView = null
    }
}

private class ObservableListCallback<T : AbstractAdapterItem>(
    adapter: UniversalAdapter<T>
) : ObservableList.OnListChangedCallback<ObservableList<T>>() {
    private val reference = WeakReference<UniversalAdapter<T>>(adapter)
    private val adapter: UniversalAdapter<T>?
        get() {
            if (Thread.currentThread() == Looper.getMainLooper().thread) return reference.get()
            else throw IllegalStateException("You must modify the ObservableList on the main thread")
        }

    override fun onChanged(sender: ObservableList<T>?) {
        adapter?.notifyDataSetChanged()
    }

    override fun onItemRangeChanged(
        sender: ObservableList<T>?,
        positionStart: Int,
        itemCount: Int
    ) {
        adapter?.notifyItemRangeChanged(positionStart, itemCount)
    }

    override fun onItemRangeInserted(
        sender: ObservableList<T>?,
        positionStart: Int,
        itemCount: Int
    ) {
        adapter?.notifyItemRangeInserted(positionStart, itemCount)
    }

    override fun onItemRangeMoved(
        sender: ObservableList<T>?,
        fromPosition: Int,
        toPosition: Int,
        itemCount: Int
    ) {
        adapter?.let {
            for (i in 0 until itemCount) {
                it.notifyItemMoved(fromPosition + i, toPosition + i)
            }
        }
    }

    override fun onItemRangeRemoved(
        sender: ObservableList<T>?,
        positionStart: Int,
        itemCount: Int
    ) {
        adapter?.notifyItemRangeRemoved(positionStart, itemCount)
    }
}