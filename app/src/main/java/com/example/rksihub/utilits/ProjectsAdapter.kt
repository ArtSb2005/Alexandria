package com.example.rksihub.utilits

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rksihub.NewActivity
import com.example.rksihub.R
import com.example.rksihub.databinding.ItemListBinding

class ProjectsAdapter(
    var c: Context, var projectList:ArrayList<ProjectData>
): RecyclerView.Adapter<ProjectsAdapter.ProjectViewHolder>()
{
    inner class ProjectViewHolder(var v: ItemListBinding): RecyclerView.ViewHolder(v.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflter = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ItemListBinding>(
            inflter, R.layout.item_list,parent,
            false)
        return ProjectViewHolder(v)

    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val newList = projectList[position]
        holder.v.isProjects = projectList[position]
        holder.v.root.setOnClickListener {
            val name = newList.name
            val info = newList.info
            val link = newList.link
            val author = newList.author
            val date = newList.date

            /**set Data*/
            val mIntent = Intent(c, NewActivity::class.java)
            mIntent.putExtra("name",name)
            mIntent.putExtra("info",info)
            mIntent.putExtra("link",link)
            mIntent.putExtra("author",author)
            mIntent.putExtra("date",date)
            c.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int {
        return  projectList.size
    }
}