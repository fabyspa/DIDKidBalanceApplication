package it.polito.ic2020.did_kidbalanceapplication.AddChild

import android.R.attr.data
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import it.polito.ic2020.did_kidbalanceapplication.R
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.activity_child_register.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.io.StringReader


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

        val fileName = "Users.txt"
        Log.i("Home", "onCreateView")
        val viewModel by activityViewModels<HomeViewModel>()
        var dataBuffer:String =""
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home, container,
                false
        )
        //chiamo il viewmodel in cui è salvato il childUser
        /*viewModel.childUser.observe(viewLifecycleOwner,Observer{
                newChild ->
            Log.i("Home","entro")
             binding.tvChild.text= newChild.name
        })*/
        //for writing Output
        //for reading
       /*context?.openFileInput(fileName).use { stream ->
            val text = stream?.bufferedReader().use {
                it?.readText()
            }
            Log.i("Home",text.toString())
        binding.tvChild.text=text.toString()
        }*/
        val xmlFile = "userData"
        val userData = ArrayList<String>()

        try {
            val fis = requireContext().openFileInput(xmlFile)
            val isr = InputStreamReader(fis)
            val inputBuffer = CharArray(fis.available())
            isr.read(inputBuffer)
            dataBuffer = String(inputBuffer)
            isr.close()
            fis.close()
        } catch (e3: FileNotFoundException) {
            e3.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        var factory: XmlPullParserFactory? = null
        try {
            factory = XmlPullParserFactory.newInstance()
        } catch (e2: XmlPullParserException) {
            // TODO Auto-generated catch block
            e2.printStackTrace()
        }
        factory!!.isNamespaceAware = true
        var xpp: XmlPullParser? = null
        try {
            xpp = factory!!.newPullParser()
        } catch (e2: XmlPullParserException) {
            e2.printStackTrace()
        }
        try {
            xpp!!.setInput(StringReader(dataBuffer))
        } catch (e1: XmlPullParserException) {
            e1.printStackTrace()
        }
        var eventType = 0
        try {
            eventType = xpp!!.eventType
        } catch (e1: XmlPullParserException) {
            e1.printStackTrace()
        }
        while (eventType != XmlPullParser.END_DOCUMENT) {
            val users= ArrayList<String>()

            if (eventType == XmlPullParser.START_DOCUMENT) {
                Log.i("Home","Start document")
            } else if (eventType == XmlPullParser.START_TAG) {
                if(xpp!!.name.toString().endsWith("_Data",false))
                        {
                            userData.add(xpp!!.name.toString())
                        }
               //Log.i("Home","Start tag " + xpp!!.name)
            } else if (eventType == XmlPullParser.END_TAG) {
                Log.i("Home","End tag " + xpp!!.name)
            } else if (eventType == XmlPullParser.TEXT) {
                Log.i("Home","text " + xpp!!.text)
                //userData.add(xpp!!.text)
            }
            try {
                eventType = xpp!!.next()
            } catch (e: XmlPullParserException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }
        Log.i("Home",userData.size.toString())
        var string=""
        for(i in userData){
            string = "$string $i"
        }

        Log.i("Home",string )
        binding.tvChild.text=string
        return binding.root

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