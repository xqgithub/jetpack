package com.example.jetpack

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mDrawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val navController = host.navController
        navigation_view.setupWithNavController(navController)


        //toolbar
        tl_main.setTitleTextColor(ContextCompat.getColor(this, R.color.appblack))
        setSupportActionBar(tl_main as Toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        //
        mDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerlayout,
            tl_main,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerlayout.addDrawerListener(mDrawerToggle)

        //NavigationView header点击
        nv_main.getHeaderView(0).setOnClickListener {
            drawerlayout.closeDrawer(nv_main)
            Toast.makeText(this, "Header View is clicked!", Toast.LENGTH_SHORT).show();
        }
        //NavigationView Item 点击
        nv_main.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHostFragment -> Toast.makeText(
                    this,
                    "首页 is clicked!",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.setttingFragment -> Toast.makeText(
                    this,
                    "订单 is clicked!",
                    Toast.LENGTH_SHORT
                ).show();

            }
            false
        }
    }
}