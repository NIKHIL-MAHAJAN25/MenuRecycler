package com.nikhil.menurecycler

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nikhil.menurecycler.categoryrecycler.Inter
import com.nikhil.menurecycler.categoryrecycler.cuisineadapter
import com.nikhil.menurecycler.databinding.FragmentAddBinding
import com.nikhil.menurecycler.dataclasses.CuisineData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFrag.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFrag : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var cadapter:cuisineadapter
    lateinit var binding: FragmentAddBinding
    var dbreference: DatabaseReference = FirebaseDatabase.getInstance().reference
    var array=ArrayList<CuisineData>()
    val TAG="logs"
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
        binding=FragmentAddBinding.inflate(layoutInflater)
        binding.btnAddnewcuis.setOnClickListener {
            if (binding.etdesc.text.isEmpty()) {
                binding.etdesc.setError("Enter desc")
            } else if (binding.mail.text.isEmpty()) {
                binding.mail.setError("Enter Name")
            } else {
                val Data =
                    CuisineData(" ", binding.mail.text.toString(), binding.etdesc.text.toString())

                dbreference.push().setValue(Data)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Added", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener { err ->
                        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                    }

            }
        }
        return binding.root
    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        binding.btnAddnewcuis.setOnClickListener {
//            if (binding.etdesc.text.isEmpty()) {
//                binding.etdesc.setError("Enter desc")
//            }
//
//            else if (binding.mail.text.isEmpty()) {
//                binding.mail.setError("Enter Name")
//            }
//            else {
//                val Data = CuisineData(" ",binding.mail.text.toString(),binding.etdesc.text.toString())
//
//                dbreference.push().setValue(Data)
//                    .addOnSuccessListener {
//                        Toast.makeText(requireContext(),"Added",Toast.LENGTH_SHORT).show()
//                    }.addOnFailureListener {err ->
//                        Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
//                    }
//
//            }
//        }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}