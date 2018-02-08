package com.rubenabad.teamworkprojects.view.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlin.properties.Delegates

/**
 * This class is wrapper over RecyclerView.Adapter. It simplifies the process of building an
 * adapter by binding an item view of the RecyclerView and its data.
 *
 * @param T The data to show in the view.
 * @param V The view to be added to the RecyclerView.
 * @property items The list of items to show.
 */
abstract class RecyclerViewAdapterBase<T, V : View> : RecyclerView.Adapter<ViewWrapper<V>>() {

    open var items: List<T> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList)
    }

    /**
     * This method wraps the item view into the ViewHolder requested by the RecyclerView.Adapter
     *
     * @param parent The ViewGroup where the View will added
     * @param viewType The type of the view
     * @return The view wrapped in the ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewWrapper<V> =
            ViewWrapper(onCreateItemView(parent, viewType))

    /**
     * Returns the total number of items provided
     *
     * @return The number of items in this adapter
     */
    override fun getItemCount(): Int = items.size

    /**
     * This abstract method is responsible of creation of the view of certain type that will be
     * added to the RecyclerView
     *
     * @param parent The ViewGroup where the View will added
     * @param viewType The type of the view
     * @return The view ready to be used by the RecyclerView
     */
    abstract fun onCreateItemView(parent: ViewGroup, viewType: Int): V

    private fun autoNotify(oldList: List<T>, newList: List<T>) {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    oldList[oldItemPosition] == newList[newItemPosition]

            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = newList.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                    oldList[oldItemPosition] == newList[newItemPosition]

        }).dispatchUpdatesTo(this)
    }

}