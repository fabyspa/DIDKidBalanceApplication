package it.polito.ic2020.did_kidbalanceapplication.AddChild

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightViewModel
import it.polito.ic2020.did_kidbalanceapplication.database.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.io.File

class homeFragment : Fragment() {
   lateinit var childWeightViewModel: ChildWeightViewModel



    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        println("SONO IN HOME")
        val navHostFragment = (this.activity?.supportFragmentManager?.findFragmentById(R.id.main_activity_container) as NavHostFragment)
        val inflaterGraph = navHostFragment.navController.navInflater
        val graph = inflaterGraph.inflate(R.navigation.navigation_login)
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_home, container, false)
        /* Vecchio modo di aggiungere bambini
        view.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_homeFragment_to_addChild2)
        }
         */

        view.genitore.setOnClickListener {
            val filename = "logIN.txt"
            val file = File(context?.filesDir?.absolutePath, filename)
            val fileExists = file.exists()
            if(fileExists){
                findNavController().navigate(R.id.action_homeFragment_to_GHomeFragment2)
            } else {
                findNavController().navigate(R.id.action_homeFragment_to_logGFragment)
            }
        }

        //Recyclerview
        val adapter= HomeAdapter()
        val recyclerView= view.recyclerView
        recyclerView.adapter= adapter
        recyclerView.layoutManager= GridLayoutManager(requireContext(), 3)

        //UserViewModel
        childWeightViewModel = ViewModelProvider(this).get(ChildWeightViewModel::class.java)
        childWeightViewModel.readAllData.observe(viewLifecycleOwner, { user ->
            adapter.setData(user)
        })

        val filename2 = "Nofigli.txt"
        val file2 = File(context?.filesDir?.absolutePath, filename2)
        val fileExists2 = file2.exists()
        if(!fileExists2){
            val fileName = "Nofigli.txt"
            //val file = File(fileName)
            File(context?.filesDir?.absolutePath,"Nofigli.txt").writeText("creato")
            findNavController().navigate(R.id.child_list_parentFragment)
        }


    return view
    }


}