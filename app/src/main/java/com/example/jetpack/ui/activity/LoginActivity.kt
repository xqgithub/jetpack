package com.example.jetpack.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jetpack.R

class LoginActivity : AppCompatActivity() {

    //lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        /*val host:NavHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        navController = host.navController*/
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //navController.navigateUp()
    }
}
