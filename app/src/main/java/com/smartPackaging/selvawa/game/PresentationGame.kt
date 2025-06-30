package com.smartPackaging.selvawa.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.smartPackaging.selvawa.game.database.AppDatabase
import com.smartPackaging.selvawa.game.entity.EntityImageQuestion
import com.smartPackaging.selvawa.game.entity.EntityMultipleChoiceQuestion
import com.smartPackaging.selvawa.game.entity.EntityTrueFalseQuestion
import kotlinx.coroutines.launch
import com.smartPackaging.selvawa.landingPage.LandingPage

class PresentationGame : AppCompatActivity() {

    /// Beneficios de ingredientes, principio de aprendizaje colectivo. Aprender sobre la nutrición

    private lateinit var buttonAJugar: Button
    private lateinit var buttonIrAPaginaPrincipal: Button
    private lateinit var db: AppDatabase
    private var avatar: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_presentation_game)
        avatar = intent.getStringExtra("avatar")
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = androidx.room.Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "preguntas-db"
        ).fallbackToDestructiveMigration().build()

        lifecycleScope.launch {
            val preguntas = db.multipleChoiceQuestionDAO().obtenerTodas()
            if (preguntas.isEmpty()) {
                db.multipleChoiceQuestionDAO().insertar(
                    EntityMultipleChoiceQuestion(
                        contenido = "¿Cuál es la capital de Francia?",
                        respuesta1 = "Madrid",
                        respuesta2 = "París",
                        respuesta3 = "Berlín",
                        respuesta4 = "Roma",
                        respuestaCorrecta = 2
                    )
                )
            }
            // Pregunta de verdadero/falso
            val vf = db.trueFalseQuestionDAO().obtenerTodas()
            if (vf.isEmpty()) {
                db.trueFalseQuestionDAO().insertar(
                    EntityTrueFalseQuestion(
                        contenido = "El sol es una estrella.",
                        esVerdadero = true
                    )
                )
            }

            // Pregunta de imagen
            val img = db.imageQuestionDAO().obtenerTodas()
            if (img.isEmpty()) {
                db.imageQuestionDAO().insertar(
                    EntityImageQuestion(
                        contenido = "¿Qué animal es este?",
                        imageUrl = "img_question1", // nombre del recurso drawable
                        respuesta1 = "Loro",
                        respuesta2 = "Perro",
                        respuesta3 = "Gato",
                        respuesta4 = "Caballo",
                        respuestaCorrecta = 1
                    )
                )
            }
        }
        initUI()
    }

    private fun initUI() {
        initUIComponents()
        initEventListeners()
    }

    private fun initUIComponents() {
        buttonAJugar = findViewById(R.id.buttonAJugar)
        buttonIrAPaginaPrincipal = findViewById(R.id.buttonIrAPaginaPrincipal)
    }

    private fun initEventListeners() {
        buttonAJugar.setOnClickListener {
            lifecycleScope.launch {
                val multiples = db.multipleChoiceQuestionDAO().obtenerTodas().shuffled().take(3)
                val verdaderoFalso = db.trueFalseQuestionDAO().obtenerTodas().shuffled().take(3)
                val imagenes = db.imageQuestionDAO().obtenerTodas().shuffled().take(4)

                val preguntas = (multiples + verdaderoFalso + imagenes).shuffled()

                val intent = Intent(this@PresentationGame, GameQuestions::class.java)
                intent.putParcelableArrayListExtra("preguntas", ArrayList(preguntas))
                intent.putExtra("avatar", avatar)
                intent.putExtra("puntos", 0)
                startActivity(intent)
                finish()
            }
        }
        buttonIrAPaginaPrincipal.setOnClickListener {
            val intent = Intent(this, LandingPage::class.java)
            startActivity(intent)
            finish()
        }
    }
}