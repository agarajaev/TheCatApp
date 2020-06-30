package com.thecat.app.favorite

import android.app.Activity
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.thecat.app.R
import com.thecat.app.data.model.Cat
import kotlinx.android.synthetic.main.list_item_fav_cat.view.*

class FavCatsAdapter(
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<FavCatsAdapter.FavCatVH>() {

    private var items: ArrayList<Cat> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCatVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_fav_cat, parent, false)

        val size = Point()
        (parent.context as Activity).windowManager.defaultDisplay.getSize(size)
        view.layoutParams.width = (size.x * 0.4).toInt()
        view.layoutParams.height = (size.x * 0.4).toInt()

        return FavCatVH(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavCatVH, position: Int) {
        val requestOptions = RequestOptions().transforms(CenterCrop(), RoundedCorners(16))
        Glide.with(holder.itemView.context)
            .load(items[position].imageUrl)
            .fitCenter()
            .apply(requestOptions)
            .into(holder.image)
        holder.name.text = items[position].name

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(
                position,
                items[position]
            )
        }
    }

    fun clear() {
        items.clear()
        this.notifyDataSetChanged()
    }

    fun setItems(items: List<Cat>) {
        this.items = items as ArrayList<Cat>
        this.notifyDataSetChanged()
    }

    class FavCatVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.image
        val name: TextView = itemView.name
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, cat: Cat)
    }
}