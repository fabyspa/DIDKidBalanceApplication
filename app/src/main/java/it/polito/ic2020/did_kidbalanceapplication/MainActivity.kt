package it.polito.ic2020.did_kidbalanceapplication

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import it.polito.ic2020.did_kidbalanceapplication.databinding.ActivityMainBinding
import it.polito.ic2020.did_kidbalanceapplication.databinding.FragmentBHomeBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.userPicture.setOnClickListener {
            it.findNavController().navigate(R.id.fragment
            )
        }
    }


}
