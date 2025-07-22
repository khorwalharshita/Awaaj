package com.example.signlanguageapp.screens


import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.signlanguageapp.R


class GameActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var scoreText: TextView
    private lateinit var livesText: TextView
    private lateinit var timerText: TextView
    private lateinit var buttons: List<Button>

    private var score = 0
    private var lives = 3
    private var correctIndex = 0
    private var countDownTimer: CountDownTimer? = null
    private lateinit var mediaPlayer: MediaPlayer

    private val letters = arrayOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
        "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
        "U", "V", "W", "X", "Y", "Z"
    )

    private val images = intArrayOf(
        R.drawable.sign_a, R.drawable.sign_b, R.drawable.sign_c, R.drawable.sign_d ,R.drawable.sign_e,


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        imageView = findViewById(R.id.signImage)
        scoreText = findViewById(R.id.scoreText)
        livesText = findViewById(R.id.livesText)
        timerText = findViewById(R.id.timerText)

        buttons = listOf(
            findViewById(R.id.option1),
            findViewById(R.id.option2),
            findViewById(R.id.option3),
            findViewById(R.id.option4)
        )

        buttons.forEach { button ->
            button.setOnClickListener {
                playSound(button.text.toString())
                if (button.text == letters[correctIndex]) {
                    score++
                    Toast.makeText(this, "✅ Correct!", Toast.LENGTH_SHORT).show()
                } else {
                    lives--
                    Toast.makeText(this, "❌ Wrong!", Toast.LENGTH_SHORT).show()
                }
                updateScoreAndLives()

                countDownTimer?.cancel()  // Stop current timer
                if (lives > 0) {
                    loadNewQuestion()
                } else {
                    endGame()
                }
            }
        }

        updateScoreAndLives()
        loadNewQuestion()
    }

    private fun loadNewQuestion() {
        correctIndex = (letters.indices).random()
        imageView.setImageResource(images[correctIndex])

        // Pick 3 wrong options + correct one
        val options = letters.indices.filter { it != correctIndex }.shuffled().take(3).toMutableList()
        options.add(correctIndex)
        options.shuffle()

        buttons.forEachIndexed { index, button ->
            button.text = letters[options[index]]
            button.isEnabled = true
        }

        startTimer()
    }

    private fun startTimer() {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(10000, 1000) {  // 10 sec countdown
            override fun onTick(millisUntilFinished: Long) {
                timerText.text = "Time: ${millisUntilFinished / 1000}s"
            }

            override fun onFinish() {
                Toast.makeText(this@GameActivity, "⏰ Time's up!", Toast.LENGTH_SHORT).show()
                lives--
                updateScoreAndLives()
                if (lives > 0) {
                    loadNewQuestion()
                } else {
                    endGame()
                }
            }
        }.start()
    }

    private fun updateScoreAndLives() {
        scoreText.text = "Score: $score"
        livesText.text = "Lives: $lives"
    }

    private fun endGame() {
        countDownTimer?.cancel()
        buttons.forEach { it.isEnabled = false }
        Toast.makeText(this, "Game Over! Your score: $score", Toast.LENGTH_LONG).show()
        // TODO: Add reset button or navigate back to menu
    }

    private fun playSound(letter: String) {
        if (::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }

        val soundResId = resources.getIdentifier(letter.lowercase(), "raw", packageName)
        if (soundResId != 0) {
            mediaPlayer = MediaPlayer.create(this, soundResId)
            mediaPlayer.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}
