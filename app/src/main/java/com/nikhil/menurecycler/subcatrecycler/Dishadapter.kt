package com.nikhil.menurecycler.subcatrecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.menurecycler.databinding.CuisineItemBinding
import com.nikhil.menurecycler.databinding.DishItemBinding
import com.nikhil.menurecycler.dataclasses.SubCuisine

class Dishadapter(var dishlist:ArrayList<SubCuisine>,var clickinter:DishInter):RecyclerView.Adapter<Dishadapter.ViewHolder>() {
    inner class ViewHolder(val binding: DishItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun binddata(position: Int)
        {
            val menu=dishlist[position]
            binding.dishTitle.text=menu.name
            binding.Dishprice.text=menu.price
            binding.maincard1.setOnLongClickListener{
                clickinter.deleteclick(menu,position)
                true
            }
            binding.maincard1.setOnClickListener{
                clickinter.editclick(menu,position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DishItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dishlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binddata(position)
    }
}