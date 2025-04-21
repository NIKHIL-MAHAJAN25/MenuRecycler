package com.nikhil.menurecycler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nikhil.menurecycler.databinding.FragmentSubCuisinesAddBinding
import com.nikhil.menurecycler.dataclasses.CuisineData
import com.nikhil.menurecycler.dataclasses.SubCuisine

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddSubCuisinesFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddSubCuisinesFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentSubCuisinesAddBinding
    var cid:String?=null
    var dbreference: DatabaseReference = FirebaseDatabase.getInstance().reference
    var array=ArrayList<SubCuisine>()
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
        binding = FragmentSubCuisinesAddBinding.inflate(layoutInflater)
        arguments?.let{
            cid=it.getString("cuisineid")
        }
        binding.etmainid.setText(cid)
        binding.adddish.setOnClickListener {
            if(binding.etdishtitle.text.isEmpty())
            {
                binding.etdishtitle.setError("Enter title")
            }
            else if (binding.etprice.text.isEmpty()) {
                binding.etprice.setError("Enter price")
            }
            else{
                val data=SubCuisine(" ",binding.etdishtitle.text.toString(),binding.etprice.text.toString(),cid)
                dbreference.child("dishes").push().setValue(data) .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { err ->
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return binding.root
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SubCuisinesFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddSubCuisinesFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}