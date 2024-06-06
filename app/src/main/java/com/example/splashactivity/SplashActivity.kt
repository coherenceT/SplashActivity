package com.example.splashactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var btnExit: Button
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        videoView = findViewById(R.id.videoView)
        btnStart = findViewById(R.id.btnStart)
        btnExit = findViewById(R.id.btnExit)

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

        btnStart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnExit.setOnClickListener {
            finish()
        }

        // Make the buttons visible when activity starts
        btnStart.visibility = View.VISIBLE
        btnExit.visibility = View.VISIBLE
    }

    // Disable the back button
    override fun onBackPressed() {
        super.onBackPressed()
        // Do nothing
    }
}
