package com.smartPackaging.selvawa.game

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TrueOrFalseQuestion : AppCompatActivity() {

    private lateinit var buttonSalirDelJuego: Button
    private lateinit var textViewPreguntaContenido: TextView
    private lateinit var gridLayoutOpciones: GridLayout

    private var contenido: String = ""
    private var esVerdadero: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_true_or_false_question)
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
        esVerdadero = intent.getBooleanExtra("esVerdadero", true)
    }

    private fun initUIComponents() {
        buttonSalirDelJuego = findViewById(R.id.buttonSalirDelJuego)
        textViewPreguntaContenido = findViewById(R.id.textViewPreguntaContenido)
        gridLayoutOpciones = findViewById(R.id.gridLayoutOpciones)
        textViewPreguntaContenido.text = contenido

        val opciones = listOf("Verdadero", "Falso")
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
                // Deshabilita todos los botones
                for (j in 0 until gridLayoutOpciones.childCount) {
                    gridLayoutOpciones.getChildAt(j).isEnabled = false
                }
                val respuestaUsuario = (i == 0) // 0: Verdadero, 1: Falso
                if (respuestaUsuario == esVerdadero) {
                    btn.setBackgroundColor(ContextCompat.getColor(this, R.color.correct_answer))
                    Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()
                } else {
                    btn.setBackgroundColor(ContextCompat.getColor(this, R.color.wrong_answer))
                    // Pinta la respuesta correcta de verde
                    val btnCorrecto = gridLayoutOpciones.getChildAt(if (esVerdadero) 0 else 1) as Button
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