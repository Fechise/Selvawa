package com.smartPackaging.selvawa.interactiveVideo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smartPackaging.selvawa.game.R
import androidx.core.net.toUri

class Option1 : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var buttonCerrar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUI()
    }

    private fun initUI() {
        initUIComponents()
        initEventListeners()
        setUpVideoView()
    }

    private fun initUIComponents() {
        videoView = findViewById(R.id.videoView)
        buttonCerrar = findViewById(R.id.buttonCerrar)
    }

    private fun initEventListeners() {
        buttonCerrar.setOnClickListener {
            showExitConfirmation()
        }
    }

    private fun showExitConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("¿Salir?")
            .setMessage("¿Estás seguro de que quieres salir del video?")
            .setPositiveButton("Sí") { _, _ ->
                val intent = Intent(this, PresentationInteractiveVideo::class.java)
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun setUpVideoView() {
        val videoUri = "android.resource://${packageName}/raw/video_option1".toUri()
        videoView.setVideoURI(videoUri)
        videoView.setOnCompletionListener {
            val intent = Intent(this, ClosingInteractiveVideo::class.java)
            startActivity(intent)
            finish()
        }
        videoView.start()
    }
}