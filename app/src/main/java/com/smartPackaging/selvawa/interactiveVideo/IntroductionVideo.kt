package com.smartPackaging.selvawa.interactiveVideo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageButton
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smartPackaging.selvawa.game.R
import com.smartPackaging.selvawa.game.EmptyOption

class IntroductionVideo : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var buttonAjiJalapeno: Button
    private lateinit var buttonAjiHabanero: Button
    private lateinit var buttonMermeladaPina: Button
    private lateinit var buttonMermeladaJamaica: Button
    private lateinit var buttonCerrar: ImageButton
    private lateinit var cardOpciones: CardView
    private lateinit var botones: List<Button>
    private lateinit var blink: Animation

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
        playIntroVideo()
    }

    private fun initUIComponents() {
        videoView = findViewById(R.id.videoView)
        buttonAjiJalapeno = findViewById(R.id.buttonAjiJalapeno)
        buttonAjiHabanero = findViewById(R.id.buttonAjiHabanero)
        buttonMermeladaPina = findViewById(R.id.buttonMermeladaPina)
        buttonMermeladaJamaica = findViewById(R.id.buttonMermeladaJamaica)
        buttonCerrar = findViewById(R.id.buttonCerrar)
        cardOpciones = findViewById(R.id.cardOpciones)
        cardOpciones.visibility = View.GONE
        botones = listOf(
            buttonAjiJalapeno,
            buttonAjiHabanero,
            buttonMermeladaPina,
            buttonMermeladaJamaica
        )
        blink = AlphaAnimation(1.0f, 0.3f).apply {
            duration = 2000
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
    }

    private fun initEventListeners() {
        buttonCerrar.setOnClickListener { showExitConfirmation() }

        buttonAjiJalapeno.setOnClickListener {
            stopBlinking()
            playAjiJalapenoVideo()
        }
        val emptyOptionListener = View.OnClickListener {
            stopBlinking()
            startActivity(Intent(this, EmptyOption::class.java))
        }
        buttonAjiHabanero.setOnClickListener(emptyOptionListener)
        buttonMermeladaPina.setOnClickListener(emptyOptionListener)
        buttonMermeladaJamaica.setOnClickListener(emptyOptionListener)
    }

    private fun playIntroVideo() {
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.video_introduction}")
        videoView.setVideoURI(videoUri)
        videoView.setOnCompletionListener {
            showOptions()
        }
        videoView.start()
    }

    private fun playAjiJalapenoVideo() {
        val videoUri = Uri.parse("android.resource://${packageName}/${R.raw.video_option_aji}")
        videoView.setVideoURI(videoUri)
        cardOpciones.visibility = View.GONE
        videoView.setOnCompletionListener {
            val intent = Intent(this, ClosingInteractiveVideo::class.java)
            startActivity(intent)
            finish()
        }
        videoView.start()
    }

    private fun showOptions() {
        cardOpciones.visibility = View.VISIBLE
        startBlinking()
    }

    private fun startBlinking() {
        botones.forEach { it.startAnimation(blink) }
    }

    private fun stopBlinking() {
        botones.forEach { it.clearAnimation() }
    }

    private fun showExitConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("¿Salir?")
            .setMessage("¿Estás seguro de que quieres salir del video interactivo?")
            .setPositiveButton("Sí") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .show()
    }
}