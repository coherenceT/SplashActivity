package com.example.splashactivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.VideoView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val screenTimes = mutableListOf<Pair<String, Pair<Int, Int>>>()

    private lateinit var btnAdd: Button
    private lateinit var btnViewDetails: Button
    private lateinit var btnClear: Button
    private lateinit var etDay: EditText
    private lateinit var etMorning: EditText
    private lateinit var etAfternoon: EditText
    private lateinit var videoView: VideoView

    private var currentVideoPosition: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoView = findViewById(R.id.videoView)
        btnAdd = findViewById(R.id.btnAdd)
        btnViewDetails = findViewById(R.id.btnViewDetails)
        btnClear = findViewById(R.id.btnClear)
        etDay = findViewById(R.id.etDay)
        etMorning = findViewById(R.id.etMorning)
        etAfternoon = findViewById(R.id.etAfternoon)

        // Set the path of the video to the VideoView
        val videoPath = "android.resource://" + packageName + "/" + R.raw.background_video
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        // Start the video
        videoView.start()

        // Loop the video
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
        }

        btnAdd.setOnClickListener {
            val day = etDay.text.toString()
            val morning = etMorning.text.toString().toIntOrNull()
            val afternoon = etAfternoon.text.toString().toIntOrNull()

            if (day.isNotEmpty() && morning != null && afternoon != null) {
                screenTimes.add(Pair(day, Pair(morning, afternoon)))
                etDay.text.clear()
                etMorning.text.clear()
                etAfternoon.text.clear()
                Toast.makeText(this, "Screen time added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show()
            }
        }

        btnViewDetails.setOnClickListener {
            val intent = Intent(this, DetailedViewActivity::class.java)
            intent.putExtra("screenTimes", ArrayList(screenTimes))
            startActivity(intent)
        }

        btnClear.setOnClickListener {
            screenTimes.clear()
            Toast.makeText(this, "Screen times cleared", Toast.LENGTH_SHORT).show()
        }
        btnClear.setOnClickListener {
            val EditText = etDay.text.clear()
            val etMorning = etMorning.text.clear()
            val etAfternoon = etAfternoon.text.clear()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Prevent back button from stopping the video
        moveTaskToBack(true)
    }

    override fun onPause() {
        super.onPause()
        // Save the current video position
        currentVideoPosition = videoView.currentPosition
        videoView.pause()
    }

    override fun onResume() {
        super.onResume()
        // Resume video from the saved position
        videoView.seekTo(currentVideoPosition)
        videoView.start()
    }
}
