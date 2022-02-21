package com.example.rksihub.utilits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rksihub.MainActivity
import com.example.rksihub.R
import com.example.rksihub.databinding.NewsItemListBinding
import com.example.rksihub.fragments.NewsFragment

class NewsAdapter(
    var c: MainActivity, var newsList:ArrayList<ProjectData>
): RecyclerView.Adapter<NewsAdapter.ProjectViewHolder>()
{
    inner class ProjectViewHolder(var v: NewsItemListBinding): RecyclerView.ViewHolder(v.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<NewsItemListBinding>(
            inflter, R.layout.news_item_list,parent,
            false)
        return ProjectViewHolder(v)

    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val newList = newsList[position]
        holder.v.isAnimals = newsList[position]
        holder.v.root.setOnClickListener {
            val name = newList.name
            val info = newList.info
            val img = newList.img

            /**set Data*/
        }
    }

    override fun getItemCount(): Int {
        return  newsList.size
    }




}