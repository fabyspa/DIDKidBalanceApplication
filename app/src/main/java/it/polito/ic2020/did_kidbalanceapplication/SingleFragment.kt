package it.polito.ic2020.did_kidbalanceapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

public abstract class SingleFragment: AppCompatActivity() {

    abstract fun createFragment() : Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_register)

        val fm:FragmentManager = supportFragmentManager
        val fragment: Fragment = fm.findFragmentById(R.id.fragmentContainer)!!

        if(fragment == null){
            val fragment= createFragment()
            fm.beginTransaction()
                    .add(R.id.fragmentContainer,fragment)
                    .commit()


    }
    }
}