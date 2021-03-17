package it.polito.ic2020.did_kidbalanceapplication.BHome

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.BoolRes
import androidx.navigation.findNavController
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.ChildActivity
import it.polito.ic2020.did_kidbalanceapplication.MainActivity
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentBHomeBinding


class BHomeFragment : Fragment() {
    private  lateinit var viewModel: BHomeViewModel

    private lateinit var binding: FragmentBHomeBinding

    public fun newInstance(): BHomeFragment {
        val args = Bundle()
        val fragment = BHomeFragment()
        fragment.arguments = args
        return fragment
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate(
             inflater,
             R.layout.fragment_b_home,container,
            false
         )
        viewModel = ViewModelProvider(this).get(BHomeViewModel::class.java)

        binding.button.setOnClickListener{
         view: View ->
         view.findNavController().navigate (R.id.action_BHomeFragment2_to_gameFragment)
        }


        // This callback will only be called when MyFragment is at least Started.
        /*
        val callback: OnBackPressedCallback =
                object : OnBackPressedCallback(true /* enabled by default */) {
                    override fun handleOnBackPressed() {
                        // Handle the back button event
                        val `in` = Intent(context, MainActivity::class.java)
                        context?.startActivity(`in`)
                    }
                }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

         */




        //binding.goHome.setOnClickListener{
            //view: View -> view.findNavController().navigate (R.id.action_BHomeFragment_to_loginFragment)
        //}
        return binding.root
    }

   

}