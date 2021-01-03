package it.polito.ic2020.did_kidbalanceapplication

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
        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.userPicture.setOnClickListener { changeUserPicture() }
    }

    private fun changeUserPicture(){
        Toast.makeText(this,"Change Image", Toast.LENGTH_SHORT).show()
    }
}
