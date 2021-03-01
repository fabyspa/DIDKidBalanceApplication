package it.polito.ic2020.did_kidbalanceapplication.Login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentAnswerBinding
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGHomeBinding
import kotlinx.android.synthetic.main.fragment_answer.*
import kotlinx.android.synthetic.main.fragment_log_g.*
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Answer.newInstance] factory method to
 * create an instance of this fragment.
 */
class Answer : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
lateinit var binding: FragmentAnswerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAnswerBinding>(inflater, R.layout.fragment_answer, container, false)
        binding.loginButton.isEnabled = false
        binding.answerLogin.addTextChangedListener(watcher)

        binding.loginButton.setOnClickListener{
            context?.openFileOutput("answer.txt", Context.MODE_APPEND)
            File(context?.filesDir?.absolutePath+"answer.txt").writeText(binding.answerLogin.text.toString())
            findNavController().navigate(R.id.action_answer4_to_navigation_login)
        }


        // Inflate the layout for this fragment
        return binding.root
    }
    private val watcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if(s.length>=3)
            binding.loginButton.isEnabled=true

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Answer.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Answer().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}