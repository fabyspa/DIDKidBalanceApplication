 package it.polito.ic2020.did_kidbalanceapplication

import android.R.attr.description
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightDatabase
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentChangePictureBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates


 // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChangePictureFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangePictureFragment : Fragment() {
    private lateinit var binding: FragmentChangePictureBinding
    private var param1: Any? = -1
    private var param2: String? = null
    companion object {
        @JvmStatic

         open fun newInstance(id: Int): Fragment {
            val fragment = ChangePictureFragment()
            val args = Bundle()
            args.putInt("id", id)
            fragment.arguments = args
            println("CPF wefqwefgqrgqergqergqergqerg " + args)
            return fragment
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreateeeee")
        arguments?.let {
            param1 = it.get("///////////////////////////////" + id)
            println(param1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // val myValue = arguments?.get("id")
        binding = DataBindingUtil.inflate<FragmentChangePictureBinding>(inflater, R.layout.fragment_change_picture, container, false)
        binding.changeProf.setOnClickListener {
            println("Click")
            Toast.makeText(this.activity, "Click!", Toast.LENGTH_SHORT).show()
            lifecycleScope.launch(Dispatchers.IO) {
                val db: ChildWeightDatabase = ChildWeightDatabase.getInstance(requireContext())
                if (param1 != -1) {
                    println("ChangePictureFragment$param1")
                    val child = db.childDataBaseDao().getAllChildData(param1.toString().toInt())
                    child.picture = binding.changeProf.id
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        println("ChangePictureAttached")
    }



}
