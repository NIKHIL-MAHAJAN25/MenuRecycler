package com.nikhil.menurecycler.categoryrecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.menurecycler.databinding.CuisineItemBinding
import com.nikhil.menurecycler.dataclasses.CuisineData

class cuisineadapter(var cuislist:ArrayList<CuisineData>, var clickinter:Inter):RecyclerView.Adapter<cuisineadapter.ViewHolder>() {
    inner class ViewHolder(val binding: CuisineItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
            fun binddata(position: Int)
            {
                val menu=cuislist[position]
                binding.CuisTitle.text=menu.name
                binding.CuisDescription.text=menu.desc
                binding.maincard.setOnLongClickListener{
                    clickinter.deleteclick(menu,position)
                    true
                }
                binding.maincard.setOnClickListener{
                    clickinter.editclick(menu,position)
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CuisineItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cuislist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binddata(position)
    }
}