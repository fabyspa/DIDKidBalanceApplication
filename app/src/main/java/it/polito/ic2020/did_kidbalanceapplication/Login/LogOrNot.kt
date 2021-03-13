package it.polito.ic2020.did_kidbalanceapplication.Login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import java.io.File

class LogOrNot : Fragment(R.layout.fragment_logornot){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filename = "name.txt"
        val file1 = File(activity?.filesDir?.absolutePath, filename)
        val file2= File(activity?.filesDir?.absolutePath,"answer.txt")
        val nameFileExists = file1.exists()
        val ansFileExists=file2.exists()//file2.length().toInt()
        //println("file answer exists: "+ file2.exists())
        //println("file size: "+file2.length().toInt())
        if(nameFileExists && ansFileExists){
            //graph.startDestination = R.id.GHomeFragment3
            findNavController().navigate(R.id.GHomeFragment3)
        } else {
            //graph.startDestination = R.id.firstPage
            findNavController().navigate(R.id.firstPage)
        }
    }
}