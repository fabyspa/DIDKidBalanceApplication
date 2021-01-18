package it.polito.ic2020.did_kidbalanceapplication

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_log_g.*
import kotlinx.android.synthetic.main.fragment_restore_pin.*
import kotlinx.android.synthetic.main.fragment_restore_pin.answer
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File

class RestorePinFragment : Fragment(R.layout.fragment_restore_pin){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var answerFile=""
        Reset_Pin.setOnClickListener {
            context?.openFileInput("logIN.txt").use { it ->
                DataInputStream(it). use { dis ->
                    while (dis.available()>0){
                        dis.readInt()
                        answerFile = dis.readLine()
                        println(answerFile)
                    }
                }
            }
            if(answer.text.toString() != answerFile){
                val filename = "logIN.txt"
                var file = File(context?.filesDir?.absolutePath, filename)
                var fileExists = file.exists()
                println(fileExists)
                file.delete()
                println(file.exists())
                findNavController().navigate(R.id.action_restorePinFragment_to_logGFragment)
            } else {
                answer.error="wrong answer, try again"
            }
        }
    }
}