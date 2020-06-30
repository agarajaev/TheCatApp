package com.thecat.app.home

import android.app.Activity
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.thecat.app.R
import com.thecat.app.data.model.Cat
import com.thecat.app.utils.paging.DiffUtilCallBack
import kotlinx.android.synthetic.main.list_item_cat.view.*

class CatsAdapter(
    private val onItemClickListener: OnItemClickListener
) : PagedListAdapter<Cat, CatsAdapter.CatVH>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_cat, parent, false)

        val size = Point()
        (parent.context as Activity).windowManager.defaultDisplay.getSize(size)
        val width = (size.x * 0.8).toInt()
        val height = (width * 12) / 11
        view.layoutParams.width = width
        view.layoutParams.height = height

        return CatVH(view)
    }

    override fun onBindViewHolder(holder: CatVH, position: Int) {
        val catItem = getItem(position) ?: return
        val requestOptions = RequestOptions().transforms(CenterCrop(), RoundedCorners(16))
        Glide.with(holder.itemView.context)
            .load(catItem.imageUrl)
            .fitCenter()
            .apply(requestOptions)
            .into(holder.image)
        holder.name.text = catItem.name

        ViewCompat.setTransitionName(holder.image, catItem.name)

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(
                holder.adapterPosition,
                catItem,
                holder.image
            )
        }
    }

    class CatVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.image
        val name: TextView = itemView.name
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: Cat, image: ImageView)
    }
}