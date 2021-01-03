package it.polito.ic2020.did_kidbalanceapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        println("LOGIN")
        bambino.setOnClickListener(){
            Toast.makeText(this,"Home Bambino", Toast.LENGTH_SHORT).show()
        }
        genitore.setOnClickListener(){
            Toast.makeText(this,"Home Genitore", Toast.LENGTH_SHORT).show()
        }
    }
}