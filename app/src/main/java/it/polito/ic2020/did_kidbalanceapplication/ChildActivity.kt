package it.polito.ic2020.did_kidbalanceapplication

import android.content.Context
import android.os.Bundle
import android.os.Debug
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import it.polito.ic2020.did_kidbalanceapplication.BHome.BHomeFragment
import it.polito.ic2020.did_kidbalanceapplication.databinding.ActivityChildBinding

class ChildActivity : AppCompatActivity(){

private lateinit var binding:ActivityChildBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val bhome: BHomeFragment = BHomeFragment().newInstance()
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment,bhome)
//                .commit()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_child)
        val extra: Bundle?= intent.extras

        if(extra!=null) {
            val childName = extra.get("name").toString()
            binding.userId.text = childName
        }
        else{
            binding.userId.text= "notFound"
        }
    }







}