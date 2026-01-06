package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    var activePlayer = (0..1).random()
    var isGameActive: Boolean = true

    var states = arrayOf(
        -1, -1, -1,
        -1, -1, -1,
        -1, -1, -1
    )

    var winningPositions = arrayOf(
        //horizontal
        arrayOf(0, 1, 2),
        arrayOf(3, 4, 5),
        arrayOf(6, 7, 8),
        //vertical
        arrayOf(0, 3, 6),
        arrayOf(1, 4, 7),
        arrayOf(2, 5, 8),
        //diagonal
        arrayOf(0, 4, 8),
        arrayOf(2, 4, 6)


    )
    private lateinit var binding: ActivityGameBinding

    private var counterX = 0
    private var counterO = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (activePlayer == 0) {
            binding.youTurnX.text = ("You turn")
            binding.youTurnO.text = ""
            binding.xCountLogo.alpha = 1.0f
            binding.xCountTv.alpha = 1.0f
            binding.oCountTv.alpha = 0.6f
            binding.oCountLogo.alpha = 0.6f


        } else if (activePlayer == 1) {
            binding.youTurnX.text = ("")
            binding.youTurnO.text = "You turn"
            binding.oCountTv.alpha = 1.0f
            binding.oCountLogo.alpha = 1.0f

            binding.xCountLogo.alpha = 0.6f
            binding.xCountTv.alpha = 0.6f


        }


    }

    fun dropIn(view: View) {
        if (isGameActive) {
            val clickedView = view as ImageView
            val tappedView = clickedView.tag.toString().toInt()

            if (states[tappedView] == -1) {
                states[tappedView] = activePlayer

                if (activePlayer == 0) {
                    binding.oCountLogo.alpha = 1.0f
                    binding.oCountTv.alpha = 1.0f
                    binding.xCountLogo.alpha = 0.6f
                    binding.xCountTv.alpha = 0.6f


                    binding.youTurnX.text = ("")
                    binding.youTurnO.text = "You turn"
                    clickedView.setImageResource(R.drawable.x)
                    activePlayer = 1
                } else {
                    binding.xCountLogo.alpha = 1.0f
                    binding.xCountTv.alpha = 1.0f
                    binding.oCountLogo.alpha = 0.6f
                    binding.oCountTv.alpha = 0.6f



                    binding.youTurnX.text = ("You turn")
                    binding.youTurnO.text = ""
                    clickedView.setImageResource(R.drawable.o)
                    activePlayer = 0
                }
            } else {
                Toast.makeText(this, "Bo'sh joyni bosing", Toast.LENGTH_SHORT).show()
            }

            for (winningPosition in winningPositions) {
                if (states[winningPosition[0]] == states[winningPosition[1]] &&
                    states[winningPosition[1]] == states[winningPosition[2]] &&
                    states[winningPosition[0]] != -1
                ) {


                    isGameActive = false

                    var winner = "X"



                    if (states[winningPosition[0]] == 1) {
                        winner = "O"
                    }
                    restartGame()

                    if (winner == "X") {
                        ++counterX
                        binding.xCountTv.text = counterX.toString()
                        showXWinCustomDialog()
                    } else if (winner == "O") {
                        ++counterO
                        binding.oCountTv.text = counterO.toString()
                        showOWinCustomDialog()
                    }


                } else {
                    var isGameOver = true
                    for (state in states) {
                        if (state == -1) {
                            isGameOver = false
                        }
                    }

                    if (isGameOver) {

                        showCustomDialog()
                        restartGame()
                    }

                }
            }


        }

    }

    fun restartGame() {

        isGameActive = true
        activePlayer = (0..1).random()
        if (activePlayer == 0) {
            binding.youTurnX.text = ("You turn")
            binding.youTurnO.text = ""
            binding.xCountLogo.alpha = 1.0f
            binding.xCountTv.alpha = 1.0f
            binding.oCountTv.alpha = 0.6f
            binding.oCountLogo.alpha = 0.6f


        } else if (activePlayer == 1) {
            binding.oCountTv.alpha = 1.0f
            binding.oCountLogo.alpha = 1.0f
            binding.youTurnX.text = ("")
            binding.youTurnO.text = "You turn"
            binding.xCountLogo.alpha = 0.6f
            binding.xCountTv.alpha = 0.6f


        }
        for (i in states.indices) {
            states[i] = -1
        }
        for (i in 0 until binding.gridLayout.childCount) {
            (binding.gridLayout.getChildAt(i) as ImageView).setImageResource(0)
        }
    }


    fun showXWinCustomDialog() {
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.x_win_dialog, null)

        val playAgain = view.findViewById<TextView>(R.id.x_play_again)
        val exitGame = view.findViewById<TextView>(R.id.x_exit_game)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()

        playAgain.setOnClickListener {
            restartGame()
            dialog.dismiss()
        }

        exitGame.setOnClickListener {
            dialog.dismiss()
            finish()
        }
    }

    fun showOWinCustomDialog() {
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.o_win_dialog, null)

        val playAgain = view.findViewById<TextView>(R.id.o_play_again)
        val exitGame = view.findViewById<TextView>(R.id.o_exit)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()

        playAgain.setOnClickListener {
            restartGame()
            dialog.dismiss()
        }

        exitGame.setOnClickListener {
            dialog.dismiss()
            finish()
        }
    }

    fun showCustomDialog() {
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.draw_dialog, null)

        val playAgain = view.findViewById<TextView>(R.id.draw_play_again)
        val exitGame = view.findViewById<TextView>(R.id.draw_exit)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()

        playAgain.setOnClickListener {
            dialog.dismiss()
        }

        exitGame.setOnClickListener {
            dialog.dismiss()
            finish()
        }
    }


}