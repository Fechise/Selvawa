package com.smartPackaging.selvawa.interactiveVideo

import android.content.Intent
import android.net.Uri
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
    private lateinit var imageViewFacebook: ImageView
    private lateinit var imageViewInstagram: ImageView

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
        imageViewFacebook = findViewById(R.id.imageViewFacebook)
        imageViewInstagram = findViewById(R.id.imageViewInstagram)
    }

    private fun initEventListeners() {
        buttonIrAPaginaPrincipal.setOnClickListener {
            val intent = Intent(this, LandingPage::class.java)
            startActivity(intent)
            finish()
        }
        imageViewFacebook.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/p/Selvawa-100067903841923/?locale=es_LA"))
            startActivity(intent)
        }
        imageViewInstagram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/selvawamazonia/"))
            startActivity(intent)
        }
    }
}