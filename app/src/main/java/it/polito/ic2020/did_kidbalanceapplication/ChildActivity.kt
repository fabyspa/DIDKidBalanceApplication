package it.polito.ic2020.did_kidbalanceapplication

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import it.polito.ic2020.did_kidbalanceapplication.BHome.BHomeViewModel
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightDatabase
import it.polito.ic2020.did_kidbalanceapplication.databinding.ActivityChildBinding
import kotlinx.android.synthetic.main.activity_child.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChildActivity : AppCompatActivity(){

private lateinit var binding:ActivityChildBinding
 var id:Int = 0

    private  lateinit var viewModel: BHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val bhome: BHomeFragment = BHomeFragment().newInstance()
//        supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment,bhome)
//                .commit()
        viewModel = ViewModelProvider(this).get(BHomeViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_child)
        val extra: Bundle?= intent.extras

        //Check Wifi Connection
        val alert = AlertDialog.Builder(this)
        alert.setTitle(resources.getString(R.string.alert_intro))
        alert.setMessage(resources.getString(R.string.alert_text))
        //alert.setPositiveButton("Ok", DialogInterface.OnClickListener(function = x))
        alert.setPositiveButton(resources.getString(R.string.yes_text)){ dialog, witch -> witch
        }
        alert.setNegativeButton(resources.getString(R.string.no_text)){ dialog, which -> onBackPressed()
        }
        alert.show()

        //nome del profilo
        if(extra!=null) {
            val childName = extra.get("name").toString()
            id =extra.get("id").toString().toInt()
            lifecycleScope.launch(Dispatchers.IO) {
                val db: ChildWeightDatabase = ChildWeightDatabase.getInstance(this@ChildActivity)
                val child = db.childDataBaseDao().getAllChildData(id)
                binding.progressBar2.progress=child.punteggio
                binding.pianetaDestinazione.text= resources.getString(R.string.testo_pianeta_destinazione) +" "+ child.planet
            }

//            bundle.putInt("id", id)
//            val fragInfo : Fragment = ChangePictureFragment.newInstance()!!
//            fragInfo.arguments = bundle
//            println("FragInfo"+ fragInfo.arguments)
            binding.userId.text = childName
            //immagine del profilo
            setImageProf(childName)
        }
        else{
            binding.userId.text= "notFound"
        }

//        viewModel.imgProfChanged.observe(LifecycleOwner(
//
//        ), Observer{ it->
//            binding.userPicture.setImageResource(it)
//        })

        binding.userPicture.setOnClickListener {
            val host: NavHostFragment? =
                    supportFragmentManager
                            .findFragmentById(R.id.main_activity_container)
            as NavHostFragment?
            val navController =host?.navController
            var bundle= bundleOf("id" to id)
            navController?.navigate(R.id.action_BHomeFragment2_to_changePictureFragment,bundle)

        }



    }
    fun setImageProf(childName:String){
        lifecycleScope.launch(Dispatchers.IO){
            val img: Int
            val db:ChildWeightDatabase= ChildWeightDatabase.getInstance(this@ChildActivity)
            img= db.childDataBaseDao().getPicture(childName)
            binding.userPicture.setImageResource(img)
            println("immagine: $img")
        }
    }










}