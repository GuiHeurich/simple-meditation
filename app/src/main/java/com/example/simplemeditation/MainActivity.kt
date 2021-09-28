package com.example.simplemeditation

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.simplemeditation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mp: MediaPlayer
    private lateinit var binding: ActivityMainBinding
    private var currentBell = mutableListOf(R.raw.bell)
    private var countDownTimer: CountDownTimer? = null
    private val displayMinutes: Minutes = Minutes("10 : 00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.displayMinutes = displayMinutes

        binding.minutesBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                binding.apply {
                    val selectedValue = progress.toString()
                    displayMinutes?.initialMinutes = "$selectedValue : 00"
                    invalidateAll()
                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {}
            override fun onStopTrackingTouch(seek: SeekBar) {}
        })

        fun playBell(id: Int) {
            mp = MediaPlayer.create(this, id)
            mp.start()
        }

        fun startCounter(secondsRemaining: Long) {
            countDownTimer = object : CountDownTimer(secondsRemaining * 60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    var diff = millisUntilFinished
                    val secondsInMilli: Long = 1000
                    val minutesInMilli = secondsInMilli * 60

                    val elapsedMinutes = diff / minutesInMilli
                    diff %= minutesInMilli

                    val elapsedSeconds = diff / secondsInMilli

                    if (elapsedSeconds < 10) {
                        binding.counterTextView.text = "$elapsedMinutes : 0$elapsedSeconds"
                    } else {
                        binding.counterTextView.text = "$elapsedMinutes : $elapsedSeconds"
                    }
                }

                override fun onFinish() {
                    binding.counterTextView.text = "Namaste"
                    playBell(id = currentBell[0])
                }
            }.start()
        }

        fun stopCountDownTimer() {
            if (countDownTimer != null) {
                countDownTimer!!.cancel()
            }
        }

        binding.beginButton.setOnClickListener {
            stopCountDownTimer()
            playBell(id = currentBell[0])
            startCounter(secondsRemaining = binding.minutesBar.progress.toLong())
        }


    }
}