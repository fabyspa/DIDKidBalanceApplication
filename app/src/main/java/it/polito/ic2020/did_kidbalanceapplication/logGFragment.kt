package it.polito.ic2020.did_kidbalanceapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentLogGBinding
import kotlinx.android.synthetic.main.activity_main_old.*
import kotlinx.android.synthetic.main.fragment_log_g.*
import java.io.BufferedOutputStream
import java.io.DataOutputStream

class logGFragment: Fragment(){
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLogGBinding> (inflater, R.layout.fragment_log_g,container,false)
            binding.setPin.isEnabled = false

            binding.pin1.doAfterTextChanged {
                if (binding.pin1.text!=null){
                    binding.setPin.isEnabled = true
                }
            }

            binding.setPin.setOnClickListener {
                    val check = pin1.text.toString().trim().length in 4..4
                if(!check) pin1.error="Insert 4 numbers"
                else if (binding.pin1.text.toString() == binding.pin2.text.toString() && answer.text.toString() != ""){
                    context?.openFileOutput(
                            "logIN.txt",
                            Context.MODE_APPEND
                    ).use { stream ->
                        DataOutputStream(BufferedOutputStream(stream)).use { dataOS ->
                            dataOS.writeInt(pin1.text.toString().toInt())
                            dataOS.writeUTF(answer.text.toString())
                            println(binding.pin1.text)
                            println(answer.text.toString())
                            println("logIN.txt scritto")
                            findNavController().navigate(R.id.action_logGFragment_to_GHomeFragment2)
                            childFragmentManager.popBackStack()
                        }
                    }
                } else if (pin2.text.toString().trim().length !in 4..4){
                    //Toast.makeText(context,"Check Pin, seems to be wrong",Toast.LENGTH_LONG).show()
                    pin2.error="Check Pin, seems to be wrong"
                } else {
                    answer.error="You forgot to answer the question"
                }
            }
        return binding.root
    }

/*
        setPin.isEnabled = false

        pin1.doAfterTextChanged {
            if (pin1.text!=null){
                setPin.isEnabled = true
            }
        }

        setPin.setOnClickListener {
            if (pin1.text == pin2.text){
                context?.openFileOutput(
                        "logIN.txt",
                        Context.MODE_APPEND
                ).use { stream ->
                    DataOutputStream(BufferedOutputStream(stream)).use { dataOS ->
                        dataOS.writeInt(pin1.text as Int)
                        println(pin1.text)
                        println("logIN.txt scritto")
                    }
                }
            }
        }

 */

}