package com.nikhil.menurecycler

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.nikhil.menurecycler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnSignIn.setOnClickListener {
            val email = binding.etemail2.text.toString()
            val password = binding.etpsswrd2.text.toString()
            auth.signInWithEmailAndPassword(
                email,
                password
            ).addOnSuccessListener {
                Toast.makeText(this, "Welcome Back Admin!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HostActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, "Invalid Username Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

