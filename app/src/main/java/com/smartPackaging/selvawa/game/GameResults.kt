package com.smartPackaging.selvawa.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smartPackaging.selvawa.landingPage.LandingPage

class GameResults : AppCompatActivity() {

    private lateinit var buttonVolverAJugar: Button
    private lateinit var buttonIrAPaginaPrincipal: Button
    private lateinit var textViewPuntos: TextView
    private var puntos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_results)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        puntos = intent.getIntExtra("puntos", 0)
        initUI()
    }

    private fun initUI() {
        initUIComponents()
        initEventListeners()
        mostrarPuntos()
    }

    private fun initUIComponents() {
        buttonVolverAJugar = findViewById(R.id.buttonVolverAJugar)
        buttonIrAPaginaPrincipal = findViewById(R.id.buttonIrAPaginaPrincipal)
        textViewPuntos = findViewById(R.id.textViewPuntos)
    }

    private fun initEventListeners() {
        buttonVolverAJugar.setOnClickListener {
            val intent = Intent(this, PresentationGame::class.java)
            startActivity(intent)
            finish()
        }
        buttonIrAPaginaPrincipal.setOnClickListener {
            val intent = Intent(this, LandingPage::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun mostrarPuntos() {
        textViewPuntos.text = getString(R.string.puntos_obtenidos, puntos)
    }
}