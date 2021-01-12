package it.polito.ic2020.did_kidbalanceapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*

class loginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        binding.change.setOnClickListener {
            val `in` = Intent(activity, BasicActivity::class.java)
            `in`.putExtra("some", "some data")
            startActivity(`in`)
        }


        binding.childView.setOnClickListener{ view: View -> view.findNavController().navigate(R.id.action_loginFragment_to_BHomeFragment)
        }
        binding.parentView.setOnClickListener{ view: View -> view.findNavController().navigate(R.id.action_loginFragment_to_GHomeFragment)
        }
        return binding.root
    }

}
