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
import com.smartPackaging.selvawa.game.entity.EntityTrueFalseQuestion

class GameQuestions : AppCompatActivity() {

    private lateinit var preguntas: ArrayList<Parcelable>
    private var indiceActual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_questions)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
        val pregunta = preguntas[indiceActual]
        val intent = when (pregunta) {
            is EntityMultipleChoiceQuestion -> {
                Intent(this, MultipleChoiceQuestion::class.java).apply {
                    putExtra("contenido", pregunta.contenido)
                    putExtra("respuesta1", pregunta.respuesta1)
                    putExtra("respuesta2", pregunta.respuesta2)
                    putExtra("respuesta3", pregunta.respuesta3)
                    putExtra("respuesta4", pregunta.respuesta4)
                    putExtra("respuestaCorrecta", pregunta.respuestaCorrecta)
                }
            }
            is EntityTrueFalseQuestion -> {
                Intent(this, TrueOrFalseQuestion::class.java).apply {
                    putExtra("contenido", pregunta.contenido)
                    putExtra("esVerdadero", pregunta.esVerdadero)
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
                }
            }
            else -> return
        }
        intent.putExtra("indice", indiceActual)
        intent.putParcelableArrayListExtra("preguntas", preguntas)
        startActivity(intent)
        finish()
    }
}