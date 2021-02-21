package it.polito.ic2020.did_kidbalanceapplication.Login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGRegisterBinding
import kotlinx.android.synthetic.main.fragment_log_g.*
import java.io.*
import kotlin.properties.Delegates


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GRegister.newInstance] factory method to
 * create an instance of this fragment.
 */
class GRegister : Fragment() {
    lateinit var name :EditText
    lateinit var passw: EditText
    lateinit var passCheck:EditText
    lateinit var button : Button
    lateinit var checkBox: CheckBox
    var filled:Boolean=false
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGRegisterBinding>(inflater, R.layout.fragment_g__register, container, false)

        name=binding.GName
        passw=binding.GPassw
        passCheck=binding.GPassCheck
        button=binding.continueButton
        checkBox=binding.checkBox
        binding.GPassCheck.toString()
        binding.continueButton.isEnabled = false
        binding.GName.addTextChangedListener(watcher)
        binding.GPassw.addTextChangedListener(watcher)
        binding.GPassCheck.addTextChangedListener(watcher)
        binding.checkBox.setOnClickListener{
            button.isEnabled = filled && checkBox.isChecked

        }

        binding.continueButton.setOnClickListener{
            Toast.makeText(this.requireContext(), "Cliccato", Toast.LENGTH_LONG).show()
            writeToFile(binding.GName.text.toString(), requireContext())
            this.findNavController().navigate(R.id.action_GRegister_to_answer4);
        }


        // Inflate the layout for this fragment
        return binding.root
    }
    private val watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
             filled= !(name.text.isEmpty() || passw.text.isEmpty() || passCheck.text.isEmpty())
        }
    }
    private fun writeToFile(data: String, context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(context.openFileOutput("name.txt", Context.MODE_PRIVATE))
            File(context?.filesDir?.absolutePath, "name.txt")
            Log.i("GRegister", context.filesDir.toString())
            outputStreamWriter.write(data)
            outputStreamWriter.close()
            readFromFile(requireContext())
        } catch (e: IOException) {
            Log.e("GRegister", "File write failed: " + e.toString())
        }
    }
    private fun readFromFile(context: Context): String? {
        var ret = ""
        try {
            val inputStream: InputStream? = context.openFileInput("name.txt")
            if (inputStream != null) {
                val inputStreamReader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                var receiveString: String? = ""
                val stringBuilder = StringBuilder()
                while (bufferedReader.readLine().also { receiveString = it } != null) {
                    stringBuilder.append("\n").append(receiveString)
                }
                inputStream.close()
                ret = stringBuilder.toString()
                Log.i("GRegister", ret)
            }
        } catch (e: FileNotFoundException) {
            Log.e("GRegister", "File not found: " + e.toString())
        } catch (e: IOException) {
            Log.e("GRegister", "Can not read file: $e")
        }
        return ret
    }


}