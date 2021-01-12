package it.polito.ic2020.did_kidbalanceapplication

import androidx.fragment.app.Fragment
import it.polito.ic2020.did_kidbalanceapplication.databinding.ActivityChildRegisterBinding
import kotlinx.android.synthetic.main.activity_child_register.*

class ChildRegister : SingleFragment() {
lateinit var binding: ActivityChildRegisterBinding
    override fun createFragment(): Fragment {
        return RecyclerFragment().newInstance()
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