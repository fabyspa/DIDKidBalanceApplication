package it.polito.ic2020.did_kidbalanceapplication.Parent

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.database.ChildWeightDatabase
import kotlinx.android.synthetic.main.fragment_edit_height.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditHeightFragment : Fragment(R.layout.fragment_edit_height){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val b = this.arguments
        val idPressed = b?.get("id").toString().toInt()
        println(idPressed)
        lifecycleScope.launch(Dispatchers.IO){
            val db: ChildWeightDatabase = ChildWeightDatabase.getInstance(requireContext().applicationContext)
            val bambinone = db.childDataBaseDao().getAllChildData(idPressed)
            withContext(Dispatchers.Main) {
                editHeight.text.append((bambinone.altezza/10).toString())
            }
        }

        setHeight.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                fun hideKeyboard() {
                    val i = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    i.hideSoftInputFromWindow(view.windowToken, 0)
                }
                val db: ChildWeightDatabase = ChildWeightDatabase.getInstance(requireContext().applicationContext)
                var bambinone = db.childDataBaseDao().getAllChildData(idPressed)
                println(bambinone)

                bambinone.altezza = editHeight.text.toString().toDouble()

                db.childDataBaseDao().update(bambinone)

                bambinone = db.childDataBaseDao().getAllChildData(idPressed)
                println("----!new bimbo ALTO!----")
                println(bambinone)
                hideKeyboard()
                findNavController().navigateUp()

            }
        }
    }
}