package it.polito.ic2020.did_kidbalanceapplication.AddChild

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.databinding.ActivityChildRegisterBinding
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.activity_child_register.*
import java.net.URL
import java.nio.charset.Charset

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentHomeBinding
    lateinit var sp: SharedPreferences
    private  lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("Home","onCreateView")
        val viewModel by activityViewModels<HomeViewModel>()

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,container,
            false
        )
       // Log.i("Home", "Called ViewModelProvider.get")
        //viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

       /* viewModel.childUser.observe(viewLifecycleOwner,Observer{
                newChild ->
            Log.i("Home","entro")
             binding.tvChild.text= newChild.name
        })*/
      //  binding.tvChild.text=str
        return binding.root
    }
    private fun onNameChanged (){
        sp = activity?.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)!!
        val savedString = sp.getString("STRING_KEY",null)
        //binding.tvChild.text= savedString
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}