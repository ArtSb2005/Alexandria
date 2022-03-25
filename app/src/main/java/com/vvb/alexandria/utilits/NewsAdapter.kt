package com.vvb.alexandria.utilits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vvb.alexandria.R
import com.vvb.alexandria.databinding.NewsItemListBinding
import com.vvb.alexandria.fragments.menu.NewsFragment

class NewsAdapter(
    var c: NewsFragment, var newsList:ArrayList<ProjectData>
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