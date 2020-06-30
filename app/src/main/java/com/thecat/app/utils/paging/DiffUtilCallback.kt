package com.thecat.app.utils.paging

import androidx.recyclerview.widget.DiffUtil
import com.thecat.app.data.model.Cat

class DiffUtilCallBack : DiffUtil.ItemCallback<Cat>() {
    override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
        return oldItem.name == newItem.name
                && oldItem.imageUrl == newItem.imageUrl
    }

}