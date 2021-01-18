package it.polito.ic2020.did_kidbalanceapplication

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Debug
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
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

        //Check Wifi Connection
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Connection Required")
        alert.setMessage("Sure you're connected to WiFi 'KidBalance'?")
        //alert.setPositiveButton("Ok", DialogInterface.OnClickListener(function = x))
        alert.setPositiveButton("yes"){
            dialog, witch -> witch
        }
        alert.setNegativeButton("no"){
            dialog, which -> onBackPressed()
        }
        alert.show()


        if(extra!=null) {
            val childName = extra.get("name").toString()
            binding.userId.text = childName
        }
        else{
            binding.userId.text= "notFound"
        }
    }







}