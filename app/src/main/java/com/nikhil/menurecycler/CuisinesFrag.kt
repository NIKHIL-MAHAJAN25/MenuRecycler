package com.nikhil.menurecycler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nikhil.menurecycler.categoryrecycler.Inter
import com.nikhil.menurecycler.categoryrecycler.cuisineadapter
import com.nikhil.menurecycler.databinding.FragmentAddBinding
import com.nikhil.menurecycler.databinding.FragmentCuisinesBinding
import com.nikhil.menurecycler.dataclasses.CuisineData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CuisinesFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class CuisinesFrag : Fragment(),Inter {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var cadapter: cuisineadapter
    lateinit var binding: FragmentCuisinesBinding
    var dbreference: DatabaseReference = FirebaseDatabase.getInstance().reference
    var array = ArrayList<CuisineData>()
    val TAG = "logs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCuisinesBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbreference.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e(TAG, "snapshot before${snapshot.value}")
                val cuisine: CuisineData? = snapshot.getValue(CuisineData::class.java)
                cuisine?.id = snapshot.key
                Log.e(TAG, " snapshot ${snapshot.value} id:${snapshot.key}")
                if (cuisine != null) {
                    array.add(cuisine)
                    cadapter.notifyDataSetChanged()

                }
                Log.e(TAG, "details ${cuisine?.name}")
            }


            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                Log.e(TAG, " on child changed")
                val cuisine: CuisineData? = snapshot.getValue(CuisineData::class.java)
                cuisine?.id = snapshot.key
                if (cuisine != null) {
                    array.forEachIndexed { index, cuisinedataa ->
                        if (cuisinedataa.id == cuisine.id) {
                            array[index] = cuisine
                            return@forEachIndexed
                        }
                    }
                    cadapter.notifyDataSetChanged()
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                Log.e(TAG, " on child removed")
                val cuisine: CuisineData? = snapshot.getValue(CuisineData::class.java)
                cuisine?.id = snapshot.key
                if (cuisine != null) {
                    array.remove(cuisine)
                    cadapter.notifyDataSetChanged()
                }

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        array.clear() 
        cadapter = cuisineadapter(array, this)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = cadapter
        binding.addNew.setOnClickListener {
            findNavController().navigate(R.id.AddFrag)

        }
    }





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CuisinesFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CuisinesFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun editclick(cuisine: CuisineData, position: Int) {

        val bundle=Bundle()
        bundle.putString("cuisineId",cuisine.id)
        findNavController().navigate(R.id.dishlist,bundle)

    }

    override fun deleteclick(cuisine: CuisineData, position: Int) {
        TODO("Not yet implemented")
    }
}