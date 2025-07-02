package com.smartPackaging.selvawa.interactiveVideo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smartPackaging.selvawa.game.R
import com.smartPackaging.selvawa.landingPage.LandingPage

class ClosingInteractiveVideo : AppCompatActivity() {

    private lateinit var buttonIrAPaginaPrincipal: Button
    private lateinit var imageViewPlay : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_closing_interactive_video)
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
    }

    private fun initUIComponents() {
        buttonIrAPaginaPrincipal = findViewById(R.id.buttonIrAPaginaPrincipal)
        imageViewPlay = findViewById(R.id.imageViewPlay)
    }

    private fun initEventListeners() {
        buttonIrAPaginaPrincipal.setOnClickListener {
            val intent = Intent(this, LandingPage::class.java)
            startActivity(intent)
            finish()
        }
        imageViewPlay.setOnClickListener {
            val intent = Intent(this, IntroductionVideo::class.java)
            startActivity(intent)
            finish()
        }
    }
}