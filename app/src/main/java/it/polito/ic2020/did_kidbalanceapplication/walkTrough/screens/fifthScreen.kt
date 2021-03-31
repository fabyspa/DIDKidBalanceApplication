package it.polito.ic2020.did_kidbalanceapplication.walkTrough.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import kotlinx.android.synthetic.main.fragment_fifth_screen.view.*

class fifthScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_fifth_screen, container, false)

        view.Gioca.setOnClickListener {
           // findNavController().navigate(R.id)
        }

        return view
    }

    private fun onBoardingFinished(){
        //setsharedPref =
    }

}