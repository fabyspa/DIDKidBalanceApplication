package it.polito.ic2020.did_kidbalanceapplication

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightDatabase
import it.polito.ic2020.did_kidbalanceapplication.databinding.ActivityChildBinding
import kotlinx.android.synthetic.main.activity_child.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChildActivity : AppCompatActivity(){

private lateinit var binding:ActivityChildBinding
 var id:Int = 0


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
        alert.setPositiveButton(yes_text){ dialog, witch -> witch
        }
        alert.setNegativeButton(no_text){ dialog, which -> onBackPressed()
        }
        alert.show()

        //nome del profilo
        if(extra!=null) {
            val childName = extra.get("name").toString()
            id =extra.get("id").toString().toInt()
            val bundle = Bundle()
            val id= id
            ChangePictureFragment.newInstance(id)
//            bundle.putInt("id", id)
//            val fragInfo : Fragment = ChangePictureFragment.newInstance()!!
//            fragInfo.arguments = bundle
//            println("FragInfo"+ fragInfo.arguments)
            binding.userId.text = childName
            //immagine del profilo
            lifecycleScope.launch(Dispatchers.IO){
                val img: Int
                val db:ChildWeightDatabase= ChildWeightDatabase.getInstance(this@ChildActivity)
                img= db.childDataBaseDao().getPicture(childName)
                binding.userPicture.setImageResource(img)
                println("immagine: $img")
            }

        }
        else{
            binding.userId.text= "notFound"
        }

        binding.userPicture.setOnClickListener {
            val host: NavHostFragment? =
                    supportFragmentManager
                            .findFragmentById(R.id.main_activity_container)
            as NavHostFragment?
            val navController =host?.navController
            navController?.navigate(R.id.action_BHomeFragment2_to_changePictureFragment)

        }



    }










}