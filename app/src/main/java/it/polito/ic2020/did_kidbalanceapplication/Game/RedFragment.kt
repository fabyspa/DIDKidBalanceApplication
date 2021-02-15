
package it.polito.ic2020.did_kidbalanceapplication.Game

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.R
import kotlinx.android.synthetic.main.fragment_green.*


class RedFragment : Fragment(R.layout.fragment_red) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentArray = arrayOf(GreenFragment::class.java, YellowFragment::class.java, BlueFragment::class.java, RedFragment::class.java)

        //get from intent
        val b = this.arguments
        var score = b?.get("score").toString().toInt()
        var count = b?.get("count").toString().toInt()
        var colors : ArrayList<String>  = arrayListOf(b?.get("colors").toString())
/*
        var score = activity?.intent!!.getIntExtra("score", -2)
        var count = activity?.intent!!.getIntExtra("count", -3)
        val colors = requireActivity().intent.getStringArrayListExtra("colors")
*/
        println(score)
        println(colors)
        println(count)

        //update score
        scoreText.text = score.toString()

        fun gameOver(newTitle: String){
            colors[count] = newTitle
            restart.visibility = View.VISIBLE
        }

        fun onCorrect(answer: String, classNum: Int){
            if(colors[count] == answer){
                val intent = Intent(this.context, fragmentArray[classNum])
                if((count+1) == colors.size) {
                    gameOver("YOU WIN")
                } else {
                    if(count == score){
                        count = -1
                        score++
                    }
                    count++
                    intent.putStringArrayListExtra("colors", colors)
                    intent.putExtra("count", count)
                    intent.putExtra("score", score)
                    startActivity(intent)
                    val intentB = bundleOf("colors" to colors, "count" to count, "score" to score)
                    when (classNum) {
                        0 -> findNavController().navigate(R.id.greenFragment, intentB)
                        1 -> findNavController().navigate(R.id.yellowFragment, intentB)
                        2 -> findNavController().navigate(R.id.blueFragment, intentB)
                        3 -> findNavController().navigate(R.id.redFragment, intentB)
                    }
                }
            } else if (restart.visibility!=0){
                gameOver("GameOver")
            }


        }

        //onCLickListeners
        green.setOnClickListener {
            onCorrect("Green",0)
        }
        yellow.setOnClickListener {
            onCorrect("Yellow",1)
        }
        blue.setOnClickListener {
            onCorrect("Blue",2)
        }
        red.setOnClickListener {
            onCorrect("Red",3)
        }
        restart.setOnClickListener {
            findNavController().navigate(R.id.gameFragment)
        }
    }
}
