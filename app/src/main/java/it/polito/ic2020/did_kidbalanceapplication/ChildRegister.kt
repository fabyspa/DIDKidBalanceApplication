package it.polito.ic2020.did_kidbalanceapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import it.polito.ic2020.did_kidbalanceapplication.databinding.ActivityChildRegisterBinding
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentAddChildBinding
import kotlinx.android.synthetic.main.activity_child_register.*
import kotlinx.android.synthetic.main.activity_main.*
class ChildRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_register)
//        setSupportActionBar(findViewById(R.id.toolbar2))


    }


//    private fun saveData() {
//        val insertedText: String = eTName.text.toString()
//        tVName.text = insertedText
//        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.apply{
//            putString("STRING_KEY",insertedText)
//        }.apply()
//    }
//
//    private fun loadData(){
//        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
//        val savedString = sharedPreferences.getString("STRING_KEY",null)
//        tVName.text= savedString
//    }

}