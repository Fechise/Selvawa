package com.smartPackaging.selvawa.game

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smartPackaging.selvawa.game.entity.EntityImageQuestion
import com.smartPackaging.selvawa.game.entity.EntityMultipleChoiceQuestion
import com.smartPackaging.selvawa.game.entity.EntityTrueOrFalseQuestion

class GameQuestions : AppCompatActivity() {

    private lateinit var preguntas: ArrayList<Parcelable>
    private lateinit var avatares: ArrayList<String>
    private var indiceActual = 0
    private var jugadorActual = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_questions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        avatares = intent.getStringArrayListExtra("avatares") ?: arrayListOf("img_player1")
        indiceActual = intent.getIntExtra("indice", 0)
        jugadorActual = intent.getIntExtra("jugadorActual", -1)
        initUI()
    }

    private fun initUI() {
        getQuestionsAndShow()
    }

    private fun getQuestionsAndShow() {
        preguntas = intent.getParcelableArrayListExtra<Parcelable>("preguntas") ?: arrayListOf()
        mostrarPreguntaActual()
    }

    private fun mostrarPreguntaActual() {
        if (indiceActual >= preguntas.size) {
            val intent = Intent(this, GameResults::class.java)
            startActivity(intent)
            finish()
            return
        }
        jugadorActual = (jugadorActual + 1) % avatares.size // Avanza turno
        val avatar = avatares[jugadorActual] // Avatar del jugador en turno
        val pregunta = preguntas[indiceActual]
        indiceActual++

        val intent = when (pregunta) {
            is EntityMultipleChoiceQuestion -> {
                Intent(this, MultipleChoiceQuestion::class.java).apply {
                    putExtra("contenido", pregunta.contenido)
                    putExtra("respuesta1", pregunta.respuesta1)
                    putExtra("respuesta2", pregunta.respuesta2)
                    putExtra("respuesta3", pregunta.respuesta3)
                    putExtra("respuesta4", pregunta.respuesta4)
                    putExtra("respuestaCorrecta", pregunta.respuestaCorrecta)
                    putExtra("avatar", avatar)
                }
            }

            is EntityTrueOrFalseQuestion -> {
                Intent(this, TrueOrFalseQuestion::class.java).apply {
                    putExtra("contenido", pregunta.contenido)
                    putExtra("esVerdadero", pregunta.esVerdadero)
                    putExtra("avatar", avatar)
                }
            }

            is EntityImageQuestion -> {
                Intent(this, ImageQuestion::class.java).apply {
                    putExtra("contenido", pregunta.contenido)
                    putExtra("imageUrl", pregunta.imageUrl)
                    putExtra("respuesta1", pregunta.respuesta1)
                    putExtra("respuesta2", pregunta.respuesta2)
                    putExtra("respuesta3", pregunta.respuesta3)
                    putExtra("respuesta4", pregunta.respuesta4)
                    putExtra("respuestaCorrecta", pregunta.respuestaCorrecta)
                    putExtra("avatar", avatar)
                }
            }

            else -> return
        }
        intent.putExtra("indice", indiceActual)
        intent.putParcelableArrayListExtra("preguntas", preguntas)
        intent.putStringArrayListExtra("avatares", avatares)
        intent.putExtra("jugadorActual", jugadorActual)
        startActivity(intent)
        finish()
    }
}