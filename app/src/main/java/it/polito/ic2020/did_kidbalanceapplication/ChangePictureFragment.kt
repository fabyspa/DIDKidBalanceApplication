 package it.polito.ic2020.did_kidbalanceapplication

import android.R.attr.description
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightDatabase
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentChangePictureBinding
import kotlinx.android.synthetic.main.activity_child.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates


 // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChangePictureFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangePictureFragment : Fragment() {
    private lateinit var binding: FragmentChangePictureBinding
    companion object {
        @JvmStatic

         open fun newInstance(id: Int): Fragment {
            val fragment = ChangePictureFragment()
            val args = Bundle()
            args.putInt("id", id)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // val myValue = arguments?.get("id")
        binding = DataBindingUtil.inflate<FragmentChangePictureBinding>(inflater, R.layout.fragment_change_picture, container, false)

        val grid: androidx.gridlayout.widget.GridLayout = binding.gridLayout
        val childCount: Int = grid.childCount

        for (i in 0 until childCount) {
            grid.getChildAt(i).setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val db: ChildWeightDatabase = ChildWeightDatabase.getInstance(requireContext())
                    //if (param1 != -1) {
                    if (arguments?.getInt("id") != null) {
                        val id = arguments?.getInt("id")
                        println("argument: " + id)
                        //println("ChangePictureFragment$param1")
                        val child = db.childDataBaseDao().getAllChildData(requireArguments().getInt("id").toInt())
                        when (i) {
                            0 -> child.picture = R.drawable.ic_b_half_moon
                            1 -> child.picture = R.drawable.ic_b_planet_earth
                            2 -> child.picture = R.drawable.ic_b_ufo
                            3 -> child.picture = R.drawable.ic_f
                            4 -> child.picture = R.drawable.ic_b_monkey
                            5 -> child.picture = R.drawable.ic_m
                            6 -> child.picture = R.drawable.ic_b_satellite
                            7 -> child.picture = R.drawable.ic_b_telescope
                            8 -> child.picture = R.drawable.ic_b_rocket
                            else -> R.drawable.kid

                        }
                        db.childDataBaseDao().update(child)
                        println(binding.changeProf.id)
                        withContext(Dispatchers.Main){
                            activity?.userPicture?.setImageResource(
                                    when (i) {
                                        0 ->  R.drawable.ic_b_half_moon
                                        1 ->  R.drawable.ic_b_planet_earth
                                        2 ->  R.drawable.ic_b_ufo
                                        3 ->  R.drawable.ic_f
                                        4 ->  R.drawable.ic_b_monkey
                                        5 ->  R.drawable.ic_m
                                        6 ->  R.drawable.ic_b_satellite
                                        7 ->  R.drawable.ic_b_telescope
                                        8 ->  R.drawable.ic_b_rocket
                                        else -> R.drawable.kid
                                    })
                            findNavController().navigateUp()
                        }

                        Log.i("CPF","IMAGE"+ binding.changeProf.drawable)



                    }
                    else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show()
                        }

                    }

                }

            }
        }

        binding.changeProf.setOnClickListener {
            println("Click")
            Toast.makeText(this.activity, "Image updated!", Toast.LENGTH_SHORT).show()
            lifecycleScope.launch(Dispatchers.IO) {
                val db: ChildWeightDatabase = ChildWeightDatabase.getInstance(requireContext())
                //if (param1 != -1) {
                if(arguments?.getInt("id")!=null){
                    val id=arguments?.getInt("id")
                    println("argument: "+id)
                    //println("ChangePictureFragment$param1")
                    val child = db.childDataBaseDao().getAllChildData(requireArguments().getInt("id").toInt())


                    child.picture = R.drawable.ic_b_half_moon
                    db.childDataBaseDao().update(child)
                    println(binding.changeProf.id)
                  //  println( " " + child.picture)
//                    activity?.finish()
//                    val `in` = Intent(activity, ChildActivity::class.java)
//                    `in`.putExtra("id", id)
//                    startActivity(`in`)
                    withContext(Dispatchers.Main){
                        activity?.userPicture?.setImageResource(R.drawable.ic_b_half_moon)
                        findNavController().navigateUp()
                    }

                    Log.i("CPF","IMAGE"+ binding.changeProf.drawable)



                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
        return binding.root
    }




}
