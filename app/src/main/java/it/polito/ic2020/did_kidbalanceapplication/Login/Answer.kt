package it.polito.ic2020.did_kidbalanceapplication.Login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentAnswerBinding
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentGHomeBinding
import kotlinx.android.synthetic.main.fragment_answer.*
import kotlinx.android.synthetic.main.fragment_log_g.*
import java.io.File


class Answer : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
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
        fun hideKeyboard() {
            val i = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            i.hideSoftInputFromWindow(view?.windowToken, 0)
        }

        binding.loginButton.setOnClickListener{
            hideKeyboard()
            context?.openFileOutput("answer.txt", Context.MODE_APPEND)
            File(context?.filesDir?.absolutePath+"answer.txt").writeText(binding.answerLogin.text.toString())
            findNavController().navigate(R.id.action_answer4_to_navigation_login)
            //findNavController().navigate(R.id.child_list_parentFragment2)
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


}