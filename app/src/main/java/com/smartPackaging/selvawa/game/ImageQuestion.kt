package com.smartPackaging.selvawa.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ImageQuestion : AppCompatActivity() {

    private lateinit var buttonSalirDelJuego: Button
    private lateinit var textViewPreguntaContenido: TextView
    private lateinit var imageViewPregunta: ImageView
    private lateinit var gridLayoutOpciones: GridLayout

    private var contenido: String = ""
    private var imageUrl: String = ""
    private var opciones: List<String> = listOf()
    private var respuestaCorrecta: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image_question)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initUI()
    }

    private fun initUI() {
        getDataIntent()
        initUIComponents()
        initEventListeners()
    }

    private fun getDataIntent() {
        contenido = intent.getStringExtra("contenido") ?: ""
        imageUrl = intent.getStringExtra("imageUrl") ?: ""
        val r1 = intent.getStringExtra("respuesta1") ?: ""
        val r2 = intent.getStringExtra("respuesta2") ?: ""
        val r3 = intent.getStringExtra("respuesta3") ?: ""
        val r4 = intent.getStringExtra("respuesta4") ?: ""
        val idxCorrecta = intent.getIntExtra("respuestaCorrecta", 1)
        opciones = listOf(r1, r2, r3, r4)
        respuestaCorrecta = idxCorrecta
    }

    private fun initUIComponents() {
        buttonSalirDelJuego = findViewById(R.id.buttonSalirDelJuego)
        textViewPreguntaContenido = findViewById(R.id.textViewPreguntaContenido)
        imageViewPregunta = findViewById(R.id.imageViewPregunta)
        gridLayoutOpciones = findViewById(R.id.gridLayoutOpciones)

        textViewPreguntaContenido.text = contenido

        val resId = resources.getIdentifier(imageUrl, "drawable", packageName)
        if (resId != 0) {
            imageViewPregunta.setImageResource(resId)
        }

        gridLayoutOpciones.removeAllViews()
        for (opcion in opciones) {
            val btn = Button(this)
            btn.text = opcion
            gridLayoutOpciones.addView(btn)
        }
    }

    private fun initEventListeners() {
        for (i in 0 until gridLayoutOpciones.childCount) {
            val btn = gridLayoutOpciones.getChildAt(i) as Button
            btn.setOnClickListener {
                for (j in 0 until gridLayoutOpciones.childCount) {
                    gridLayoutOpciones.getChildAt(j).isEnabled = false
                }
                if (i + 1 == respuestaCorrecta) {
                    btn.setBackgroundColor(ContextCompat.getColor(this, R.color.correct_answer))
                    Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()
                } else {
                    btn.setBackgroundColor(ContextCompat.getColor(this, R.color.wrong_answer))
                    val btnCorrecto = gridLayoutOpciones.getChildAt(respuestaCorrecta - 1) as Button
                    btnCorrecto.setBackgroundColor(ContextCompat.getColor(this, R.color.correct_answer))
                    Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
        }
        buttonSalirDelJuego.setOnClickListener {
            startActivity(Intent(this, PresentationGame::class.java))
        }
    }
}