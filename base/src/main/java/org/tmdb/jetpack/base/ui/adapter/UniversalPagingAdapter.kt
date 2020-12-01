package org.tmdb.jetpack.base.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Now you do not need to write an adapter every time
 * Use this universal adapter that uses binding underhood
 *
 * Steps how to use
 * - Create adapter item inherited from [AbstractAdapterItem]
 *      override ```fun layoutId()```
 * - In item's layout in <data> section declare variable with name "item" with type of created adapter item
 *      Example
 *      ```
 *          <data>
 *              <variable
 *                  name="item"
 *                  type="xxx.xxxx.YourAdapterItem"
 *          </data>
 *      ```
 * - In view model create LiveData<PagedList<AbstractAdapterItem>> property from datasource
 *   factory provided by repository. See  https://developer.android.com/topic/libraries/architecture/paging#data
 * - Set this items to RecyclerView using binding
 *   ```
 *      <androidx.recyclerview.widget.RecyclerView
 *          app:pagedItems="@{viewmodel.fileItems}
 *          app: comparator="@{YourComparator}"
 */
class UniversalPagingAdapter<ITEM : AbstractAdapterItem>
constructor(
    diffUtilCallback: DiffUtil.ItemCallback<ITEM>
) : PagingDataAdapter<ITEM, RecyclerView.ViewHolder>(diffUtilCallback){

    var selectionTracker: SelectionTracker<Long>? = null

    override fun getItemViewType(position: Int): Int {
        return getItem(position)!!.layoutId()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UniversalViewHolder) {
            val item = getItem(position)!!.apply {
                itemId = position.toLong()
            }
            val isSelected = selectionTracker?.isSelected(item.itemId) ?: false
            holder.bind(item, isSelected)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return UniversalViewHolder(binding)
    }
}