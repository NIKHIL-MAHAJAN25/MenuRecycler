package com.nikhil.menurecycler

import android.app.AlertDialog
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
import com.nikhil.menurecycler.databinding.FragmentDishBinding
import com.nikhil.menurecycler.dataclasses.CuisineData
import com.nikhil.menurecycler.dataclasses.SubCuisine
import com.nikhil.menurecycler.subcatrecycler.DishInter
import com.nikhil.menurecycler.subcatrecycler.Dishadapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DishFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DishFragment : Fragment(),DishInter {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentDishBinding
    lateinit var dadapter: Dishadapter
    var id: String? = null
    var dbref: DatabaseReference = FirebaseDatabase.getInstance().reference
    var array1 = ArrayList<SubCuisine>()
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
        binding = FragmentDishBinding.inflate(layoutInflater)
        arguments?.let {
            id = it.getString("cuisineId")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddDish.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("cuisineid", id)
            findNavController().navigate(R.id.fragmentaddsubcuisine, bundle)
        }
        dbref.child("dishes").orderByChild("cdishid").equalTo(id)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    Log.e(TAG, "snapshot before${snapshot.value}")
                    val subcuisine: SubCuisine? = snapshot.getValue(SubCuisine::class.java)
                    subcuisine?.id = snapshot.key
                    Log.e(TAG, " snapshot ${snapshot.value} id:${snapshot.key}")
                    if (subcuisine != null) {
                        array1.add(subcuisine)
                        dadapter.notifyDataSetChanged()

                    }
                    Log.e(TAG, "details ${subcuisine?.name}")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val subCuisine: SubCuisine? = snapshot.getValue(SubCuisine::class.java)
                    subCuisine?.id = snapshot.key
                    if (subCuisine != null) {
                        array1.forEachIndexed { index, subCuisine1 ->
                            if (subCuisine1.id == subCuisine.id) {
                                array1[index] = subCuisine
                                return@forEachIndexed
                            }
                        }
                        dadapter.notifyDataSetChanged()
                    }
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val subCuisine: SubCuisine? = snapshot.getValue(SubCuisine::class.java)
                    subCuisine?.id = snapshot.key
                    if (subCuisine != null) {
                        array1.removeAll { it.id == subCuisine.id }
                        dadapter.notifyDataSetChanged()

                    }
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        array1.clear()
        dadapter = Dishadapter(array1, this)
        binding.Dishrecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.Dishrecycler.adapter = dadapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DishFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DishFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun editclick(subCuisine: SubCuisine, position: Int) {
        TODO("Not yet implemented")
    }

    override fun deleteclick(subCuisine: SubCuisine, position: Int) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Delete")
        dialog.setMessage("Are you sure you want to delete this dish?")
        dialog.setPositiveButton("Yes") { addDialog, _ ->
            subCuisine.id?.let { key ->
                dbref.child("dishes").child(key).removeValue()
                    .addOnSuccessListener {
                        if (position >= 0 && position < array1.size) {
                            array1.removeAt(position)
                            dadapter.notifyItemRemoved(position)
                            Log.e(TAG, "Dish deleted successfully")
                        }else{
                            Log.e(TAG, "Invalid position: $position")
                        }
                    }
                    .addOnFailureListener {
                        Log.e(TAG, "Failed to delete dish: ${it.message}")
                    }
            }
        }
        dialog.setNegativeButton("No") { addDialog, _ ->
            addDialog.dismiss()
        }
        dialog.create()
        dialog.show()
    }
}
