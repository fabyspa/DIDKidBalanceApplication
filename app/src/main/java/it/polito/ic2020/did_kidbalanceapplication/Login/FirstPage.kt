package it.polito.ic2020.did_kidbalanceapplication.Login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import kotlinx.android.synthetic.main.fragment_first_page.*


class FirstPage : Fragment(R.layout.fragment_first_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAccount.setOnClickListener {
            findNavController().navigate(R.id.GRegister)
        }
    }


}