package com.smartPackaging.selvawa.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PresentationGame : AppCompatActivity() {

    private lateinit var buttonAJugar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_presentation_game)
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
        buttonAJugar = findViewById(R.id.buttonAJugar)
    }

    private fun initEventListeners() {
        buttonAJugar.setOnClickListener {
            startActivity(Intent(this, MultipleChoiceQuestion::class.java))
            finish()
        }
    }
}