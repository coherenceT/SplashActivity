package com.example.splashactivity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.splashactivity.R

class DetailedViewActivity : AppCompatActivity() {

    private lateinit var btnBack: Button
    private lateinit var tvDetails: TextView
    private lateinit var tvAverage: TextView
    private lateinit var videoView: VideoView

    private var currentVideoPosition: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)

        btnBack = findViewById(R.id.btnBack)
        tvDetails = findViewById(R.id.tvDetails)
        tvAverage = findViewById(R.id.tvAverage)
        videoView = findViewById(R.id.videoView)

        // Set the path of the video to the VideoView
        val videoPath = "android.resource://${packageName}/${R.raw.background_video}"
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        // Start the video
        videoView.start()

        // Loop the video
        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
        }

        // Retrieve screenTimes from intent
        val screenTimes = intent.getSerializableExtra("screenTimes") as ArrayList<Pair<String, Pair<Int, Int>>>

        // Display screenTimes details
        val details = StringBuilder()
        var totalMorning = 0
        var totalAfternoon = 0

        screenTimes.forEach { (day, times) ->
            details.append("Day: $day\nMorning: ${times.first} minutes\nAfternoon: ${times.second} minutes\n\n")
            totalMorning += times.first
            totalAfternoon += times.second
        }

        val averageMorning = if (screenTimes.isNotEmpty()) totalMorning / screenTimes.size else 0
        val averageAfternoon = if (screenTimes.isNotEmpty()) totalAfternoon / screenTimes.size else 0

        tvDetails.text = details.toString()
        tvAverage.text = "Average Morning: $averageMorning minutes\nAverage Afternoon: $averageAfternoon minutes"

        // Set up back button animation and listener
        btnBack.apply {
            setBackgroundColor(ContextCompat.getColor(context, R.color.buttoncolor))
            setTextColor(Color.WHITE)
            setOnClickListener {
                finish()
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            }
        }

        // Add fade-in animation to details and average text views
        fadeInView(tvDetails)
        fadeInView(tvAverage)

        // Add zoom-in animation to the video view
        zoomInView(videoView)
    }

    private fun fadeInView(view: View) {
        view.alpha = 0f
        view.animate()
            .alpha(1f)
            .setDuration(1500)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }

    private fun zoomInView(view: View) {
        val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.2f, 1f)
        scaleX.duration = 1500
        scaleY.duration = 1500
        scaleX.interpolator = AccelerateDecelerateInterpolator()
        scaleY.interpolator = AccelerateDecelerateInterpolator()
        scaleX.start()
        scaleY.start()
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
