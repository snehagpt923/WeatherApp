package com.example.weatherapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.Blog
import com.example.weatherapp.util.loadImage

class MyRecyclerViewAdapter constructor(context: Context, data: List<Blog>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    private val mData: List<Blog> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mData[position].apply {
            holder.tvTitle.text = title
            holder.tvBody.text = body
            holder.imageView.loadImage(image)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvTitle = itemView.findViewById(R.id.tvTitle) as TextView
        var tvBody = itemView.findViewById(R.id.tvBody) as TextView
        var imageView = itemView.findViewById(R.id.imageView) as ImageView

        override fun onClick(view: View) {
            mClickListener?.onItemClick(view, mData[adapterPosition])
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, blog: Blog)
    }
}