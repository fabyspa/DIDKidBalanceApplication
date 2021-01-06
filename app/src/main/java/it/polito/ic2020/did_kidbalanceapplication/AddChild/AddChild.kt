package it.polito.ic2020.did_kidbalanceapplication.AddChild

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentAddChildBinding
import kotlinx.android.synthetic.main.fragment_add_child.*

class AddChild : Fragment(R.layout.fragment_add_child) {

    lateinit var binding: FragmentAddChildBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel by activityViewModels<HomeViewModel>()

        saveName.setOnClickListener{
            viewModel.addUser(
                et_name.text.toString()
            )
            findNavController().navigate(R.id.action_addChild_to_home2)
        }
        // Inflate the layout for this fragment
        /*binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_child,container,
            false*/


    }

}