package org.tmdb.jetpack.base.ui.adapter

import org.tmdb.jetpack.base.core.utils.SingleLiveEvent
import java.lang.IllegalArgumentException

abstract class AbstractAdapterItem {

    /**
     * Layout of item
     */
    abstract fun layoutId(): Int

    /**
     * Id of item declared as variable in data section of layout [layoutId]
     * Example:
     * ```
     * <layout>
     *     <data>
     *         <variable
     *              name="item"
     *              type="xx.xx..xx.YourAdapterItem" />
     *     </data>
     *    <androidx.constraintlayout.widget.ConstraintLayout />
     * <layout>
     * ```
     */
    abstract fun variableId(): Int

    var itemId: Long = -1
    var domainModel: Any? = null
    var onClickEvent: SingleLiveEvent<out Any>? = null

    /**
     * To handle click event from recycler item root container should call this method.
     * Example:
     * ```
     * <androidx.constraintlayout.widget.ConstraintLayout
     *  android:onClick="@{() -> item.OnItemClick()}">
     *
     *  [onItemClick] event returns to subscriber either [domainModel] if not null or item itself
     */
    open fun onItemClick() {
        onClickEvent?.let { if (domainModel != null ) it.value = domainModel else it.value = this  }
            ?: throw IllegalArgumentException("onItemClick() can't be called if onClickEvent is null")
    }
}