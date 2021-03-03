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

        val alert_intro = resources.getString(R.string.alert_intro)
        val alert_text = resources.getString(R.string.alert_text)
        val yes_text = resources.getString(R.string.yes_text)
        val no_text = resources.getString(R.string.no_text)


                //Check Wifi Connection
        val alert = AlertDialog.Builder(this)
        alert.setTitle(alert_intro)
        alert.setMessage(alert_text)
        //alert.setPositiveButton("Ok", DialogInterface.OnClickListener(function = x))
        alert.setPositiveButton(yes_text){
            dialog, witch -> witch
        }
        alert.setNegativeButton(no_text){
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