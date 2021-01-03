package it.polito.ic2020.did_kidbalanceapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentBHomeBinding
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [loginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class loginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //prima di fare questa cosa devi andare in fragment_login.xml e mettere <layout> esterno, vai a vedere (purtrollo nell xml non posso scrivere commenti
        //il  <layou> </layout> l'ho aggiunto io, prima c'era solo il FrameLayout
        val binding = DataBindingUtil.inflate<FragmentLoginBinding> (inflater, R.layout.fragment_login,container,false)

        binding.childView.setOnClickListener{
                view: View -> view.findNavController().navigate (//percorso)
        }
        return binding.root
    }

}


}