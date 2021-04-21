package it.polito.ic2020.did_kidbalanceapplication.Game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Layer
import androidx.navigation.Navigation
import java.util.*
import androidx.navigation.fragment.findNavController
import it.polito.ic2020.did_kidbalanceapplication.ChildActivity
import it.polito.ic2020.did_kidbalanceapplication.R
import kotlinx.android.synthetic.main.fragment_circle_game.view.*


class GameLib @SuppressLint("SetTextI18n") internal constructor(//Need config
    private var nb_bloc_start: Int,
    nb_bloc_4_win: Int,
    private var life: Int,
    poids_du_mode: Double,
    buttons: List<Button>,
    arrayColor: IntArray,
    lvl: Int,
    lbl_score: TextView,
    lbl_life: TextView,
    btn_start: Button,
    lbl_round: TextView,
    score: Double,
    chrono: Boolean,
    lbl_timer: TextView,
    returnHome: Button,
    //hide_game2: View
) {
    private var checkPosition = 0
    private var roundCounter = 0
    private val colors = IntArray(50)
    private var secuencePosition = 0
    private val def_score: Double
    var score = 0.0
        private set
    private var vivant = false
    var win = false
        private set

    //MODE CHRONO ONLY
    private val chrono: Boolean
    private var timerSec: Long = 0
    private val lbl_timer: TextView
    private var timer: CountDownTimer? = null

    //Declaration of buttons:
    /* public Button btnBlue;
    public Button btnGreen;
    public Button btnYellow;
    public Button btnRed;
    private Button btnStart;
*/
    private val lbl_life: TextView
    private val btn_start: Button
    private val returnHome: Button
    //private val hide_game2: View

    // private Button btn_new;
    private val lbl_round: TextView

    //Scoreboards / txt info
    private val score_value: TextView
    private val life_TextView: TextView
    var end = false
        private set

    private val nb_bloc_4_win: Int
    private val lvl: Int
    private val poids_du_mode: Double

    //list of gaming buttons
    private val buttons: List<Button>

    //list of color in same order than gaming button
    private val arrayColor: IntArray

    //Verif si les button existe
    private fun checkSiButtonExist() {
        for (button in buttons) {
            val checking = button.text.toString()
            Log.i("checkfor :", checking)
        }
    }

    //creer la sequence du  simon
    private fun newSequence() {
        // 4 the win :
        if (vivant && roundCounter == nb_bloc_4_win) {
            win = true
            message(btn_start, "You WIN")
            println("win: "+win)
            end()
        } else {
            val rd = Random()
            val possibleColors = arrayColor


            //Setup tour 1 avec le  nb_bloc_start
            if (roundCounter == 0) {
                for (i in 0 until nb_bloc_start) {
                    colors[roundCounter] = possibleColors[rd.nextInt(buttons.size)]
                    roundCounter++
                }
            } else {
                //creer une nouvelle couleurs est l ajoute au tableau au numero du round
                colors[roundCounter] = possibleColors[rd.nextInt(buttons.size)]
                roundCounter++
            }

            //Gere affichage du round
            lbl_round.text = "ROUND: $roundCounter"


            //Affiche la séquence
            setSequence()
            vivant = true
            checkPosition = 0
        }
    }

    //Affiche la sequence sur le boutton
    private fun setSequence() {
        val background: View = btn_start
        showSequence(background)


        //set le boutton à gris et active les autres boutton
        background.postDelayed({
            updateBackground(Color.GRAY)
            background.alpha = 1f
            for (myButton in buttons) {
                myButton.isEnabled = true
            }
        }, (roundCounter + 1) * 500.toLong())
        if (chrono) {
            Log.i("Chrono :", "on")
            timerSec = roundCounter * 2 * 1000 + 1000.toLong()
            startTimer()
        }

        //Disable le button start
        btn_start.isEnabled = false
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timerSec, 100) {
            override fun onTick(millisUntilFinished: Long) {
                timerSec = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                life--
                //sinon mauvaise couleur vivant = faux (on meurt)
                life_TextView.text = "life :$life"
                if (life <= 0) vivant = false
                if (life > 0) newGame()
                isDead
            }
        }.start()
    }

    private fun pauseTimer() {
        timer!!.cancel()
    }

    private fun updateTimer() {
        lbl_timer.text = timerSec.toString().substring(0, timerSec.toString().length - 2)
    }

    //change le background du buttonStart
    private fun updateBackground(color: Int) {
        btn_start.setBackgroundColor(color)
    }

    //cette methode commence le jeux
    private fun newGame() {
        if(life<=0){end()}
        //Set le background du start à gris
        val startButton = btn_start
        startButton.setBackgroundColor(Color.CYAN)

        //reset les score et round
        roundCounter = 0
        score = def_score


        //Affiche le score
        score_value.text = "SCORE: $score"

        //Affiche les vie
        life_TextView.text = "Life :$life"

        //active le btn start
        startButton.isEnabled = true
        startButton.visibility = View.VISIBLE

        //Affiche le round
        lbl_round.text = "ROUND: $roundCounter"
    }

    //Verif si la couleur clicker est correct sinon affiche win/lose
    private fun colorCheck(color: Int) {


        //verif si game a start et non mort
        if (vivant) {
            //Si la couleur bonne
            if (color == colors[checkPosition]) {
                //bonne couleur : possition+1. Ajout de point au score et affiche le score
                checkPosition++
                // score += 10;
                // score_value.setText("SCORE: " + score);
            } else {
                if (chrono) pauseTimer()
                //sinon mauvaise couleur vivant = faux (on meurt)
                life--
                life_TextView.text = "life :$life"
                if (life <= 0) vivant = false
                if (life > 0) newGame()
            }
            isDead
        }
    }//Ajout au score 2* le round
    // Log.v("SCORE : " , lvl +"*"+poids_du_mode );
    //actualise le score

    /*//Active le button start
    startButton.setEnabled(true);*/
    //Disable les button de "jeux"

    // Si mort :
    //si on est pas mort ajouts des points et désactivation des button
    private val isDead: Unit
        private get() {
            //si on est pas mort ajouts des points et désactivation des button
            if (checkPosition == roundCounter && vivant) {
                if (chrono) pauseTimer()
                //Ajout au score 2* le round
                // Log.v("SCORE : " , lvl +"*"+poids_du_mode );
                score += lvl * poids_du_mode
                //actualise le score
                score_value.text = "SCORE: $score"
                newSequence()

                /*//Active le button start
                startButton.setEnabled(true);*/
                //Disable les button de "jeux"
                for (myButton in buttons) myButton.isEnabled = false
            }

            // Si mort :
            if (!vivant && roundCounter > 0) {
                if (chrono) pauseTimer()
                message(btn_start, "You lose")
                btn_start.isClickable = false
                btn_start.setBackgroundColor(Color.GRAY)
                println("win: "+win)
                returnHome.visibility = View.VISIBLE
                //hide_game2.visibility = View.VISIBLE
                //end()
                //newGame()

            }
        }

    private fun end() {
        end = true
    }

    //Send des MSG fin de partie avec le boutton "start"
    private fun message(startButton: Button, msg: String) {
        startButton.postDelayed({ //Efface les buttons de "jeux"
            for (myButton in buttons) myButton.visibility = View.INVISIBLE


            //Efface le lbl round
            lbl_round.visibility = View.INVISIBLE

            //Efface le lbl Life
            lbl_life.visibility = View.INVISIBLE

            //Affiche you lose sur le button "start"
            startButton.setBackgroundColor(Color.BLACK)
            startButton.setTextColor(Color.WHITE)
            startButton.text = msg
        }, 50)
        startButton.postDelayed({ //reset le button start
            startButton.text = "Start"
            startButton.setBackgroundColor(Color.GRAY)
            startButton.setTextColor(Color.BLACK)


            //Affiche le round
            val t = lbl_round
            t.text = "ROUND: 0"

            //Efface le lbl Life
            lbl_life.visibility = View.VISIBLE

            //Set le button de "jeux" sur visible
            for (myButton in buttons) myButton.visibility = View.VISIBLE
            t.visibility = View.VISIBLE
        }, 3500)


        //reset l'affichage du score
        score_value.text = "SCORE: $score"

        //Start new Game
        // newGame();

        //Active le button start
        startButton.isEnabled = true
    }

    //Highlight start button
    private fun showSequence(background: View) {
        secuencePosition = 0
        //Affiche chaque couleur
        for (i in 0 until roundCounter) {

            //Degrade de couleur
            if (colors[i] != 0) {
                background.postDelayed({
                    updateBackground(colors[secuencePosition])
                    background.alpha = 0.2f
                }, (i + 1) * 500.toLong())
                background.postDelayed({ background.alpha = 0.5f }, 500 * (i + 1) + 100.toLong())
                background.postDelayed({ background.alpha = 0.9f }, 500 * (i + 1) + 150.toLong())
                background.postDelayed({ background.alpha = 1f }, 500 * (i + 1) + 250.toLong())
                background.postDelayed({ background.alpha = 0.9f }, 500 * (i + 1) + 350.toLong())
                background.postDelayed({ background.alpha = 0.5f }, 500 * (i + 1) + 400.toLong())
                background.postDelayed({
                    background.alpha = 0.2f
                    secuencePosition++
                }, 500 * (i + 1) + 450.toLong())
            }
        }
    }

    private fun colorHighlight(button: Button) {
        button.postDelayed({ button.alpha = 0.5f }, 0)
        button.postDelayed({ button.alpha = 0.75f }, 50)
        button.postDelayed({ button.alpha = 1f }, 100)
        button.postDelayed({ button.alpha = 0.75f }, 250)
        button.postDelayed({ button.alpha = 0.5f }, 300)
    }

    init {
        //  super.onCreate(savedInstanceState);
        // setContentView(R.layout.game);
        nb_bloc_start = nb_bloc_start
        this.nb_bloc_4_win = nb_bloc_4_win
        this.poids_du_mode = poids_du_mode
        this.buttons = buttons
        this.arrayColor = arrayColor
        this.lvl = lvl
        this.lbl_life = lbl_life
        this.btn_start = btn_start
        this.returnHome = returnHome
        //this.hide_game2 = hide_game2
        // this.btn_new = btn_new;
        this.lbl_round = lbl_round
        def_score = score
        this.chrono = chrono
        this.lbl_timer = lbl_timer
        //The value of the buttons and scoreboard is asigned by its id in the content_main.xml file
        //fetchButtons();

        //  buttons = Arrays.asList(btnBlue, btnGreen, btnYellow, btnRed);
        checkSiButtonExist()


        //Set score
        score_value = lbl_score
        score_value.text = "SCORE:$score"

        //Set life
        life_TextView = lbl_life
        btn_start.visibility = View.INVISIBLE


        //Manages button activation
        var i = 0
        //loop sur tout les bouttons
        for (myButton in buttons) {
            val myColor = arrayColor[i]
            myButton.setBackgroundColor(myColor)
            myButton.alpha = 0.5f
            i++
        }

        //Manages start button activation
        btn_start.setOnClickListener {
            newSequence()
            println("Btn_start Pressed")
        }

        //Manages new game button activation
        /*btn_new.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                newGame();
            }
        });*/newGame()


        //Manages button activation
        i = 0
        //loop sur tout les bouttons
        for (myButton in buttons) {
            val myColor = arrayColor[i]

            //set event onclick
            myButton.setOnClickListener {
                colorCheck(myColor)
                colorHighlight(myButton)
            }
            i++
        }

        /*    final int final_nb_bloc_4_win = nb_bloc_4_win;
        final Button final_btn_start = btn_start;





        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                // 4 the win :
                if (vivant && roundCounter == final_nb_bloc_4_win) {
                    final Button message =  final_btn_start;
                    win = true;
                    message(message, "You WIN");

                }else {
                    newSequence();
                }
            }

        });*/
        val thread: Thread = object : Thread() {
            override fun run() {}
        }
        thread.start()
    }
}