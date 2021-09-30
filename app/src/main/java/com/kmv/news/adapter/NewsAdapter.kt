package com.kmv.news

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kmv.news.data.News

class NewsAdapter constructor(private val listener: NewsItemListener, val context: Context, items: ArrayList<News>)
    :RecyclerView.Adapter<NewsViewHolder>(){
    var item =items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_list_item,parent,false)
        val viewHolder = NewsViewHolder(view)

        view.setOnClickListener(){
            listener.onItemClicked(item[viewHolder.adapterPosition])
        }
        return viewHolder;
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
       Log.d("TAG","Size of list ${item.size}")
       val currentitem = item[position]
       holder.textitem.text = currentitem.title
       holder.author.text = currentitem.author

       Glide.with(holder.itemView.context).load(currentitem.urlToImage).into(holder.image)
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun updateNews(updateList : ArrayList<News>){
        item.clear()
        item.addAll(updateList)
        notifyDataSetChanged()
    }
}
 class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val textitem = itemView.findViewById<TextView>(R.id.title)
    val image = itemView.findViewById<ImageView>(R.id.image)
    val author = itemView.findViewById<TextView>(R.id.author)
}

interface NewsItemListener {
    fun onItemClicked(item : News)
}