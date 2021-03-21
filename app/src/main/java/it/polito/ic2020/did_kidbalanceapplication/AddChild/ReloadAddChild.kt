package it.polito.ic2020.did_kidbalanceapplication.AddChild

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R

class ReloadAddChild : Fragment(R.layout.fragment_reload_addchild){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().navigate(R.id.action_reloadAddChild_to_addChild2)
    }
}