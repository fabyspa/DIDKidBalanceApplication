package it.polito.ic2020.did_kidbalanceapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import it.polito.ic2020.did_kidbalanceapplication.databinding.ActivityChildRegisterBinding
import it.polito.ic2020.did_kidbalanceapplication.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_child_register.*

class ChildRegister : AppCompatActivity() {
lateinit var binding: ActivityChildRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView<ActivityChildRegisterBinding>(this, R.layout.activity_child_register)

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