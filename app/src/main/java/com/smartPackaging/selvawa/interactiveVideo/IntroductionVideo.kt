package com.smartPackaging.selvawa.interactiveVideo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smartPackaging.selvawa.game.R
import androidx.core.net.toUri

class IntroductionVideo : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var buttonOpcion1: Button
    private lateinit var buttonOpcion2: Button
    private lateinit var buttonCerrar: ImageButton
    private lateinit var cardOpciones: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction_video)
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
        buttonOpcion1 = findViewById(R.id.buttonOpcion1)
        buttonOpcion2 = findViewById(R.id.buttonOpcion2)
        buttonCerrar = findViewById(R.id.buttonCerrar)
        cardOpciones = findViewById(R.id.cardOpciones)
        cardOpciones.visibility = View.GONE
    }

    private fun initEventListeners() {
        buttonOpcion1.setOnClickListener {
            intent = intent.setClass(this, Option1::class.java)
            startActivity(intent)
        }
        buttonOpcion2.setOnClickListener {
            // Acción para Opción 2
        }
        buttonCerrar.setOnClickListener {
            showExitConfirmation()
        }
    }

    private fun showExitConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("¿Salir?")
            .setMessage("¿Estás seguro de que quieres salir del video interactivo?")
            .setPositiveButton("Sí") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .show()
    }

    private fun setUpVideoView() {
        val videoUri = "android.resource://${packageName}/raw/video_introduction".toUri()
        videoView.setVideoURI(videoUri)
        videoView.setOnCompletionListener {
            cardOpciones.visibility = View.VISIBLE
        }
        videoView.start()
    }
}