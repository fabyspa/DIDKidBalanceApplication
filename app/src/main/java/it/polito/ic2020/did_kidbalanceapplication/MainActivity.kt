package it.polito.ic2020.did_kidbalanceapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment = (supportFragmentManager.findFragmentById(R.id.main_activity_container) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.login_register_navigation)
        val filename = "name.txt"
        val file1 = File(this.filesDir?.absolutePath, filename)
        val file2= File(this.filesDir?.absolutePath,"answer.txt")
        val nameFileExists = file1.exists()
        val ansFileExists=file2.exists()//file2.length().toInt()
        println("file answer exists: "+ file2.exists())
        println("file size: "+file2.length().toInt())
        if(nameFileExists&&ansFileExists){
            graph.startDestination = R.id.GHomeFragment3
        } else {
            graph.startDestination = R.id.firstPage
        }
        navHostFragment.navController.graph = graph

    }

    /*
    override fun onStart() {
        super.onStart()
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.userPicture.setOnClickListener {
            it.findNavController().navigate(R.id.navigation_login)
        }
    }

     */


}
