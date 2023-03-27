package com.wyattrussellmiller.pig

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    //create win value to use throughout the application
    val winValue = 100

    //Create formatter for capturing the time when a winner is declared
    private val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")

    //Create variable to check if you should pass extra intent
    var gameOver = false

    //Create key variables for saving state
    private val ACTIVE_PLAYER = "ACTIVE_PLAYER"

    private val PLAYER_WINS = "PLAYER_WINS"
    private val PLAYER_TOTAL_SCORE = "PLAYER_TOTAL_SCORE"
    private val PLAYER_TURN_SCORE = "PLAYER_TURN_SCORE"

    private val COMPUTER_WINS = "COMPUTER_WINS"
    private val COMPUTER_TOTAL_SCORE = "COMPUTER_TOTAL_SCORE"
    private val COMPUTER_TURN_SCORE = "COMPUTER_TURN_SCORE"

    private val DICE1 = "DICE1"
    private val DICE2 = "DICE2"

    private val CURRENT_SCORE = "CURRENT_SCORE"

    private val WIN_LOSE_IMAGE = "WIN_LOSE_IMAGE"

    //Create late init vars for text views
    private lateinit var playerToPlayTV: TextView
    private lateinit var playerWinsTV: TextView
    private lateinit var playerTotalScoreTV: TextView
    private lateinit var playerTurnScoreTV: TextView

    private lateinit var computerToPlayTV: TextView
    private lateinit var computerWinsTV: TextView
    private lateinit var computerTotalScoreTV: TextView
    private lateinit var computerTurnScoreTV: TextView

    private lateinit var currentScoreTV: TextView

    //create values for both text views and use throughout the application
    private var playerWins = 0
    var playerTotalScore = 0
    private var playerTurnScore = 0

    private var computerWins = 0
    var computerTotalScore = 0
    private var computerTurnScore = 0

    private var currentScore = 0

    //create late init vars for buttons
    lateinit var rollBtn: Button
    lateinit var holdBtn: Button
    lateinit var leaderboardBtn: Button

    //Create late init vars for image views
    private lateinit var dice1: ImageView
    private lateinit var dice2: ImageView
    private lateinit var winLoseImage: ImageView

    //Create variable for active player.
    //True for player one false for player two.
    var activePlayer = true

    //Create dice variables
    private var rollDice1 = 1
    private var rollDice2 = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Call function to add starting data to the variables
        fillViews()
    }

    override fun onSaveInstanceState(savedState: Bundle) {
        super.onSaveInstanceState(savedState)

        //Save needed data to properly restore with
        savedState.putBoolean(ACTIVE_PLAYER, activePlayer)

        savedState.putInt(PLAYER_WINS, playerWins)
        savedState.putInt(PLAYER_TOTAL_SCORE, playerTotalScore)
        savedState.putInt(PLAYER_TURN_SCORE, playerTurnScore)

        savedState.putInt(COMPUTER_WINS, computerWins)
        savedState.putInt(COMPUTER_TOTAL_SCORE, computerTotalScore)
        savedState.putInt(COMPUTER_TURN_SCORE, computerTurnScore)

        savedState.putInt(CURRENT_SCORE, currentScore)

        savedState.putInt(DICE1, rollDice1)
        savedState.putInt(DICE2, rollDice2)

        savedState.putBoolean(WIN_LOSE_IMAGE, winLoseImage.isVisible)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        //Restore variable data
        activePlayer = savedInstanceState.getBoolean(ACTIVE_PLAYER)

        playerWins = savedInstanceState.getInt(PLAYER_WINS)
        playerTotalScore = savedInstanceState.getInt(PLAYER_TOTAL_SCORE)
        playerTurnScore = savedInstanceState.getInt(PLAYER_TURN_SCORE)

        computerWins = savedInstanceState.getInt(COMPUTER_WINS)
        computerTotalScore = savedInstanceState.getInt(COMPUTER_TOTAL_SCORE)
        computerTurnScore = savedInstanceState.getInt(COMPUTER_TURN_SCORE)

        currentScore = savedInstanceState.getInt(CURRENT_SCORE)

        rollDice1 = savedInstanceState.getInt(DICE1)
        rollDice2 = savedInstanceState.getInt(DICE2)

        winLoseImage.isVisible = savedInstanceState.getBoolean(WIN_LOSE_IMAGE)

        //make sure correct win/lost image is set
        if (playerTotalScore >= winValue) {
            winLoseImage.setImageResource(R.drawable.winimg)
        } else {
            winLoseImage.setImageResource(R.drawable.loseimg)
        }

        //Put variables back into views
        fillViews()

        //Check if it was the computers turn. If so, reset turn score and call computers turn again
        if (!savedInstanceState.getBoolean(ACTIVE_PLAYER) && playerTotalScore < winValue) {
            computerTurnScore = 0
            computersTurn()
        }

        if (playerTotalScore >= winValue || computerTotalScore >= winValue) {
            rollBtn.isEnabled = false
            holdBtn.isEnabled = false
        }
    }

    private fun fillViews() {
        //Link Late init variables to text views
        playerToPlayTV = findViewById(R.id.youPlay)
        playerWinsTV = findViewById(R.id.playerWins)
        playerTotalScoreTV = findViewById(R.id.playerTotalScore)
        playerTurnScoreTV = findViewById(R.id.playerTurnScore)

        computerToPlayTV = findViewById(R.id.comPlay)
        computerWinsTV = findViewById(R.id.computerWins)
        computerTotalScoreTV = findViewById(R.id.computerTotalScore)
        computerTurnScoreTV = findViewById(R.id.computerTurnScore)

        currentScoreTV = findViewById(R.id.currentScore)

        //Link Late init variables to buttons
        rollBtn = findViewById(R.id.rollDice)
        holdBtn = findViewById(R.id.holdTurn)
        leaderboardBtn = findViewById(R.id.showLeaderboard)

        //Link Late init variables to image views
        dice1 = findViewById(R.id.dice1)
        dice2 = findViewById(R.id.dice2)
        winLoseImage = findViewById(R.id.winLoseImage)

        //Set starting text view values
        playerWinsTV.text = playerWins.toString()
        playerTotalScoreTV.text = playerTotalScore.toString()
        playerTurnScoreTV.text = playerTurnScore.toString()

        computerWinsTV.text = computerWins.toString()
        computerTotalScoreTV.text = computerTotalScore.toString()
        computerTurnScoreTV.text = computerTurnScore.toString()

        currentScoreTV.text = currentScore.toString()

        //Set starting image view values
        setDiceImage(rollDice1, rollDice2)

        //highlight player to prompt them to take the first turn
        if (activePlayer) {
            playerToPlayTV.setBackgroundColor(Color.CYAN)
            computerToPlayTV.setBackgroundColor(Color.WHITE)
        } else {
            computerToPlayTV.setBackgroundColor(Color.CYAN)
            playerToPlayTV.setBackgroundColor(Color.WHITE)
        }

        //disable hold button so player must roll at least once
        //Extra logic included for when state is restored
        if (activePlayer && playerTurnScore == 0) {
            holdBtn.isEnabled = false
        } else if (activePlayer && playerTurnScore > 0) {
            holdBtn.isEnabled = true
        }
    }

    fun rollDice(v: View) {

        //create dice and roll into the dice variables then set dice images
        val d6 = Dice(6)
        rollDice1 = d6.roll()
        rollDice2 = d6.roll()

        setDiceImage(rollDice1, rollDice2)

        //display dice total
        currentScore = (rollDice1 + rollDice2)
        currentScoreTV.text = currentScore.toString()

        //determine who to add the turn total to
        if (activePlayer) {
            playerTurnScore += currentScore
            playerTurnScoreTV.text = playerTurnScore.toString()
            holdBtn.isEnabled = true
        } else {
            computerTurnScore += currentScore
            computerTurnScoreTV.text = computerTurnScore.toString()
        }

        //check if any 1s were rolled
        //if so reset needed scores and pass turn
        if (rollDice1 == 1 || rollDice2 == 1) {
            if (activePlayer) {
                playerTurnScore = 0
                playerTurnScoreTV.text = playerTurnScore.toString()

                if (rollDice1 == 1 && rollDice2 == 1) {
                    playerTotalScore = 0
                    playerTotalScoreTV.text = playerTotalScore.toString()
                }

            } else {
                computerTurnScore = 0
                computerTurnScoreTV.text = computerTurnScore.toString()

                if (rollDice1 == 1 && rollDice2 == 1) {
                    computerTotalScore = 0
                    computerTotalScoreTV.text = computerTotalScore.toString()
                }
            }
            holdBtn.performClick()
        }
    }

    private fun setDiceImage(rollDice1: Int, rollDice2: Int) {
        //set first dice image
        when (rollDice1) {
            1 -> dice1.setImageResource(R.drawable.dice_1)
            2 -> dice1.setImageResource(R.drawable.dice_2)
            3 -> dice1.setImageResource(R.drawable.dice_3)
            4 -> dice1.setImageResource(R.drawable.dice_4)
            5 -> dice1.setImageResource(R.drawable.dice_5)
            6 -> dice1.setImageResource(R.drawable.dice_6)
        }

        //set second dice image
        when (rollDice2) {
            1 -> dice2.setImageResource(R.drawable.dice_1)
            2 -> dice2.setImageResource(R.drawable.dice_2)
            3 -> dice2.setImageResource(R.drawable.dice_3)
            4 -> dice2.setImageResource(R.drawable.dice_4)
            5 -> dice2.setImageResource(R.drawable.dice_5)
            6 -> dice2.setImageResource(R.drawable.dice_6)
        }
    }

    fun holdTurn(v: View) {
        //check active player, then update variables needed to change active player
        if (activePlayer) {
            playerTotalScore += playerTurnScore
            playerTotalScoreTV.text = playerTotalScore.toString()

            playerTurnScore = 0
            playerTurnScoreTV.text = playerTurnScore.toString()

            computerToPlayTV.setBackgroundColor(Color.CYAN)
            playerToPlayTV.setBackgroundColor(Color.WHITE)

            activePlayer = false

            //Start computers turn. Skip if someone has won
            if (playerTotalScore < winValue && computerTotalScore < winValue) {
                computersTurn()
            }
        } else {
            computerTotalScore += computerTurnScore
            computerTotalScoreTV.text = computerTotalScore.toString()

            computerTurnScore = 0
            computerTurnScoreTV.text = computerTurnScore.toString()

            playerToPlayTV.setBackgroundColor(Color.CYAN)
            computerToPlayTV.setBackgroundColor(Color.WHITE)

            activePlayer = true
        }

        //check to see if either player has won
        checkWin()
    }

    private fun computersTurn() {
        //disable buttons so player can't play
        rollBtn.isEnabled = false
        holdBtn.isEnabled = false

        //create count down object to make computer roll three times
        //Do not roll or hold if it isn't the computers turn
        //Do not enable roll button if the game is over
        object : CountDownTimer(6000, 2000) {

            override fun onTick(millisUntilFinished: Long) {
                if (!activePlayer) {
                    rollBtn.performClick()
                }
            }

            override fun onFinish() {
                if (!activePlayer) {
                    holdBtn.performClick()
                }

                if (playerTotalScore < winValue && computerTotalScore < winValue) {
                    rollBtn.isEnabled = true
                }
            }
        }.start()
    }

    private fun checkWin() {
        //Check to see if player won or lost
        //set image and score accordingly
        //make reset image visible
        if (playerTotalScore >= winValue) {
            playerWins += 1
            playerWinsTV.text = playerWins.toString()
            winLoseImage.setImageResource(R.drawable.winimg)

            rollBtn.isEnabled = false
            holdBtn.isEnabled = false

            winLoseImage.isVisible = true
        } else if (computerTotalScore >= winValue) {
            computerWins += 1
            computerWinsTV.text = computerWins.toString()
            winLoseImage.setImageResource((R.drawable.loseimg))

            rollBtn.isEnabled = false
            holdBtn.isEnabled = false

            winLoseImage.isVisible = true
        }
    }

    fun newGame(v: View) {

        gameOver = true
        leaderboardBtn.performClick()
        gameOver = false

        //re-set needed variables to starting values
        activePlayer = true

        playerToPlayTV.setBackgroundColor(Color.CYAN)
        computerToPlayTV.setBackgroundColor(Color.WHITE)

        playerTotalScore = 0
        playerTotalScoreTV.text = playerTotalScore.toString()

        computerTotalScore = 0
        computerTotalScoreTV.text = computerTotalScore.toString()

        dice1.setImageResource(R.drawable.dice_1)
        dice2.setImageResource(R.drawable.dice_1)

        currentScore = 0
        currentScoreTV.text = currentScore.toString()

        rollBtn.isEnabled = true

        winLoseImage.isVisible = false

    }

    //swap to leaderboard activity
    fun ShowLeaderboardOnClick(v: View) {
        val intent = Intent(this, leaderboard::class.java)

        //check if game is over, if so add extra intent to add to leaderboard.txt file
        if (gameOver) {
            val winner: String
            val score: Int

            if (playerTotalScore > computerTotalScore) {
                winner = "You"
                score = playerTotalScore
            } else {
                winner = "Computer"
                score = computerTotalScore
            }
            intent.putExtra("winner", winner)
            intent.putExtra("score", score)
            intent.putExtra("date", LocalDateTime.now().format(formatter))
        }
        startActivity(intent)
    }
}

//adjust the range of the dice if you don't want to roll 1s
class Dice(private var numSides: Int) {
    fun roll(): Int {
        return (1..numSides).random()
    }
}