package com.nikhil.menurecycler.subcatrecycler

import com.nikhil.menurecycler.dataclasses.CuisineData
import com.nikhil.menurecycler.dataclasses.SubCuisine

interface DishInter {
    fun editclick(subCuisine: SubCuisine, position:Int)
    fun deleteclick(subCuisine: SubCuisine, position:Int)
}