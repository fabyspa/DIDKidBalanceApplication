package it.polito.ic2020.did_kidbalanceapplication.BHome

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.BoolRes
import androidx.navigation.findNavController
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentBHomeBinding


class BHomeFragment : Fragment() {
    private  lateinit var viewModel: BHomeViewModel

    private lateinit var binding: FragmentBHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate(
             inflater,
             R.layout.fragment_b_home,container,
            false
         )
        Log.i("GameFragment", "Called ViewModelProvider.get")

        viewModel = ViewModelProvider(this).get(BHomeViewModel::class.java)
        viewModel.actionDayDone.observe(viewLifecycleOwner, {
                hasBeenClicked-> if(hasBeenClicked) binding.dayActionButton.isEnabled= false
        })
        binding.button.setOnClickListener{
         view: View ->
         view.findNavController().navigate (R.id.action_BHomeFragment_to_gameFragment)
        }

        binding.dayActionButton.setOnClickListener{onDisableButton(this.view)}

        //binding.goHome.setOnClickListener{
            //view: View -> view.findNavController().navigate (R.id.action_BHomeFragment_to_loginFragment)
        //}
        return binding.root
    }

    fun onDisableButton(view: View?){
        Toast.makeText(activity, "actionDayDone", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(R.id.action_BHomeFragment_to_BDayFragment)
        viewModel.onActionDayDone()


    }

}