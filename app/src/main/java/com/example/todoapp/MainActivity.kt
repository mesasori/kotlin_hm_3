package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setupActionBarWithNavController(findNavController(R.id.fragment_container))
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)
                    as NavHostFragment
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        controller = navHostFragment.navController
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }*/
}