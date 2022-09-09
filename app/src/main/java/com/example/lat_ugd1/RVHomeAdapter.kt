package com.example.lat_ugd1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lat_ugd1.entity.Home

class RVHomeAdapter(private val data: Array<Home>) : RecyclerView.Adapter<RVHomeAdapter.viewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder{
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_home, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int){
        val currentItem = data[position]
        holder.ivImage.setImageResource(currentItem.img)
        holder.tvLowongan.text = currentItem.lowongan
        holder.tvDetail.text = "${currentItem.nama} || ${currentItem.alamat}"
        holder.tvDetail2.text = currentItem.gaji

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val ivImage: ImageView = itemView.findViewById(R.id.iv_img)
        val tvLowongan : TextView = itemView.findViewById(R.id.tv_detail)
        val tvDetail : TextView = itemView.findViewById(R.id.tv_detail)
        val tvDetail2 : TextView = itemView.findViewById(R.id.tv_detail2)
    }
}