package it.polito.ic2020.did_kidbalanceapplication.walkTrough.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import kotlinx.android.synthetic.main.fragment_fifth_screen.*
import kotlinx.android.synthetic.main.fragment_fifth_screen.view.*

class fifthScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_fifth_screen, container, false)

        view.Gioca.setOnClickListener {
            //Toast.makeText(this.context,"Ok bene",Toast.LENGTH_LONG).show()
            onBoardingFinished()
            waitGame.visibility=View.VISIBLE
            findNavController().navigate(R.id.action_viewPagerFragment2_to_gameCircle2)
        }

        return view
    }

    private fun onBoardingFinished(){
        val id = requireActivity().intent!!.extras?.get("id").toString().toInt()
        println("from fifth: "+id)
        val sharedPref = requireActivity().getSharedPreferences("onBoarding"+id,Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }

}