package com.example.fotografpaylasmafirebase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fotografpaylasmafirebase.R
import com.example.fotografpaylasmafirebase.model.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_row.view.*

class AnaMenuRecyclerAdapter(val postList:ArrayList<Post>):RecyclerView.Adapter<AnaMenuRecyclerAdapter.PostHolder>() {

    class PostHolder(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_row,parent,false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.itemView.recycler_row_kullamici_email.text=postList[position].kullaniciEmail
        holder.itemView.recycler_row_yorum.text=postList[position].kullaniciYorum
        Picasso.get().load(postList[position].gorselUrl).into(holder.itemView.recycler_row_imageview)

    }

    override fun getItemCount(): Int {
        return postList.size

    }
}