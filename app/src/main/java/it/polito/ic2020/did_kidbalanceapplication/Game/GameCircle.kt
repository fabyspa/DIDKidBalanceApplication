package it.polito.ic2020.did_kidbalanceapplication

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import it.polito.ic2020.did_kidbalanceapplication.Game.GameLib
import kotlinx.android.synthetic.main.fragment_circle_game.*
import java.lang.String
import java.util.*
import java.util.concurrent.TimeUnit

class GameCircle : Fragment(R.layout.fragment_circle_game) {
    //Need config
    private var nb_bloc_start = 1
    private var nb_bloc_4_win = 7
    private var default_life = 2
    private var poids_du_mode = 1.toDouble()
    private var score = 0.0
    private var chrono = false
    private var lvl = 1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val extras: Bundle? = this.arguments
        if (extras != null) {
            nb_bloc_start = extras.getInt("nb_bloc_start", 1)
            nb_bloc_4_win = extras.getInt("nb_bloc_4_win",7)
            default_life = extras.getInt("default_life", 2)
            poids_du_mode = extras.getDouble("poids_du_mode", 1.toDouble())
            lvl = extras.getInt("lvl", 1)
            score = extras.getDouble("score", 0.0)
            chrono = extras.getBoolean("chrono", false)
        }
        //if (chrono) findViewById(R.id.timer).setVisibility(View.VISIBLE)
        val numButton: Int = lvl + 3
        val arrayColor = intArrayOf(
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.CYAN,
                Color.MAGENTA,
                Color.rgb(255, 102, 204),
                Color.rgb(102, 51, 51),
                Color.rgb(51, 51, 0),
                Color.rgb(102, 153, 153)
        )
        val buttons: MutableList<Button> = ArrayList()
        val main: FrameLayout = view.findViewById(R.id.main)
        val displayMetrics = DisplayMetrics()
        activity?.getWindowManager()?.getDefaultDisplay()?.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        val size: Int
        size = if (height <= width) {
            height
        } else {
            width
        }
        for (i in 0 until numButton) {
            /* Create some quick TextViews that can be placed. */
            val button = Button(this.context)
            buttons.add(button)

            // Set a text and center it in each view.
            //button.setText(getString(R.string.couleur) + i)
            button.gravity = Gravity.CENTER
            button.setBackgroundColor(arrayColor[i])

            // Force the views to a nice size (150x100 px) that fits my display.
            // This should of course be done in a display size independent way.
            val lp = FrameLayout.LayoutParams(
                    (size * 0.30).toInt(),
                    (size * 0.15).toInt()
            )

            // Place all views in the center of the layout. We'll transform them
            // away from there in the code below.
            lp.gravity = Gravity.CENTER

            // Set layout params on view.
            button.layoutParams = lp

            // Calculate the angle of the current view. Adjust by 90 degrees to
            // get View 0 at the top. We need the angle in degrees and radians.
            val angleDeg = i * 360.0f / numButton - 90.0f
            val angleRad = (angleDeg * Math.PI / 180.0f).toFloat()

            // Calculate the position of the view, offset from center (300 px from
            // center). Again, this should be done in a display size independent way.
            button.setTranslationX(
                    ((size * 0.3 * Math.cos(angleRad.toDouble()).toFloat()).toInt()).toFloat()
            )
            button.setTranslationY(
                    ((size * 0.3 * Math.sin(angleRad.toDouble()).toFloat()).toInt()).toFloat()
            )

            // Set the rotation of the view.
            button.rotation = angleDeg + 90.0f
            main?.addView(button)
        }

/*
        //Get some shit on layout
        val lbl_score: TextView? = view?.findViewById(R.id.lbl_score)
        val lbl_life: TextView? = view?.findViewById(R.id.lbl_life)
        val btn_start: Button? = view?.findViewById(R.id.btn_start)
        val lbl_round: TextView? = view?.findViewById(R.id.lbl_round)
        val lbl_timer: TextView? = view?.findViewById(R.id.timer)
        val lbl_lvl: TextView? = view?.findViewById(R.id.lbl_lvl)

 */
        //lbl_lvl.setText(getString(R.string.level) + lvl)
        Log.e("score", score.toString())
        //Setup class
        val game = GameLib(
                nb_bloc_start,
                nb_bloc_4_win,
                default_life,
                poids_du_mode,
                buttons,
                arrayColor,
                lvl,
                lbl_score,
                lbl_life,
                btn_start,
                lbl_round,
                score,
                chrono,
                timer
        )


        //quantique
        val thread: Thread = object : Thread() {
            override fun run() {
                do {
                    try {
                        TimeUnit.SECONDS.sleep(1)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                } while (!game.end)
                if (game.win) {
                    //get next view  for game
                    val startGame: Intent = Intent()
                    startGame.putExtra("lvl", lvl + 1)
                    startGame.putExtra("score", game.score)
                    startActivityForResult(startGame, lvl + 1)
                    Log.i("scorend", String.valueOf(game.score))
                } else {
                    val intent = Intent()
                    intent.putExtra("exit", true)
                    //setResult(RESULT_OK, intent)
                    //finish()
                    Toast.makeText(context, "Maybe you're a looser", Toast.LENGTH_LONG).show()
                }
            }
        }
        thread.start()
    }

    internal fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == lvl + 1) {
            if (resultCode == RESULT_OK) {
                val exit = data.extras!!.getBoolean("exit")
                if (exit) {
                    val intent = Intent()
                    intent.putExtra("exit", true)
                    //setResult(RESULT_OK, intent)
                    //finish()
                }
            }
        }
    }

    fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("exit", true)
        //setResult(RESULT_OK, intent)
        //finish()
    }
}