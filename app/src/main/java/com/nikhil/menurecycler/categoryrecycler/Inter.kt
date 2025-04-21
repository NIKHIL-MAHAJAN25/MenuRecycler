package com.nikhil.menurecycler.categoryrecycler

import com.nikhil.menurecycler.dataclasses.CuisineData

interface Inter {
    fun editclick(cuisine: CuisineData, position:Int)
    fun deleteclick(cuisine: CuisineData, position:Int)
}