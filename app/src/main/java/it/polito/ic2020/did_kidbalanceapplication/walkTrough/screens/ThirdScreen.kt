package it.polito.ic2020.did_kidbalanceapplication.walkTrough.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import it.polito.ic2020.did_kidbalanceapplication.R
import kotlinx.android.synthetic.main.fragment_first_screen.view.*

class ThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_third_screen, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        view.Avanti.setOnClickListener {
            viewPager?.currentItem = 3

        }

        return view
    }

}