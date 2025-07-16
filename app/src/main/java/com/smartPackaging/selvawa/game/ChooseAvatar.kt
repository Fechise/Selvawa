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
import com.smartPackaging.selvawa.game.entity.EntityTrueOrFalseQuestion
import kotlinx.coroutines.launch
import android.widget.Toast
import android.widget.ImageView
import kotlin.inc
import kotlin.text.compareTo
import android.widget.TextView

class ChooseAvatar : AppCompatActivity() {

    private lateinit var buttonAJugar: Button
    private lateinit var db: AppDatabase
    private lateinit var avatarOsoHormiguero: ImageView
    private lateinit var avatarJaguar: ImageView
    private lateinit var avatarTucan: ImageView
    private lateinit var avatarPerezoso: ImageView
    private var avataresSeleccionados = mutableListOf<String>()
    private var jugadorActual = 1
    private var totalJugadores = 1
    private var avatarSeleccionado: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_choose_avatar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        totalJugadores = intent.getIntExtra("totalJugadores", 1)

        db = androidx.room.Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "preguntas-db-v2"
        ).fallbackToDestructiveMigration().build()

        lifecycleScope.launch {
            // Preguntas de opción múltiple
            val preguntasMultiple = db.multipleChoiceQuestionDAO().obtenerTodas()
            if (preguntasMultiple.isEmpty()) {
                db.multipleChoiceQuestionDAO().insertar(
                    listOf(
                        EntityMultipleChoiceQuestion(
                            contenido = "¿Cuál es el ingrediente principal del ají frito de Selvawa?",
                            respuesta1 = "Sacha culantro",
                            respuesta2 = "Tomate riñón",
                            respuesta3 = "Ají amazónico",
                            respuesta4 = "Palmito",
                            respuestaCorrecta = 3
                        ),
                        EntityMultipleChoiceQuestion(
                            contenido = "¿Qué hace al sacha cilantro diferente del cilantro común?",
                            respuesta1 = "Su aroma es más suave",
                            respuesta2 = "Se cultiva solo en la Sierra",
                            respuesta3 = "Tiene sabor fuerte y anisado",
                            respuesta4 = "No se usa en cocina",
                            respuestaCorrecta = 3
                        ),
                        EntityMultipleChoiceQuestion(
                            contenido = "¿Qué significa el nombre Selvawa?",
                            respuesta1 = "Espíritu de la selva",
                            respuesta2 = "Salsa picante",
                            respuesta3 = "Trabajo comunitario",
                            respuesta4 = "Planta medicinal",
                            respuestaCorrecta = 1
                        ),
                        EntityMultipleChoiceQuestion(
                            contenido = "¿Desde qué parte del Ecuador viene el ají de Selvawa?",
                            respuesta1 = "Sierra central",
                            respuesta2 = "Selva amazónica ecuatoriana",
                            respuesta3 = "Costa norte",
                            respuesta4 = "Galápagos",
                            respuestaCorrecta = 2
                        ),
                        EntityMultipleChoiceQuestion(
                            contenido = "¿Qué valor promueve a Selvawa a trabajar con comunidades?",
                            respuesta1 = "Productividad",
                            respuesta2 = "Tradición familiar",
                            respuesta3 = "Comercio justo y sostenibilidad",
                            respuesta4 = "Innovación tecnológica",
                            respuestaCorrecta = 3
                        )
                    )
                )
            }

            // Preguntas verdadero/falso
            val preguntasVF = db.trueFalseQuestionDAO().obtenerTodas()
            if (preguntasVF.isEmpty()) {
                db.trueFalseQuestionDAO().insertar(
                    listOf(
                        EntityTrueOrFalseQuestion(
                            contenido = "Selvawa trabaja con ingredientes cultivados por comunidades locales.",
                            esVerdadero = true
                        ),
                        EntityTrueOrFalseQuestion(
                            contenido = "El sacha cilantro y el cilantro común son exactamente iguales.",
                            esVerdadero = false
                        ),
                        EntityTrueOrFalseQuestion(
                            contenido = "El ají frito de Selvawa contiene ingredientes artificiales.",
                            esVerdadero = false
                        ),
                        EntityTrueOrFalseQuestion(
                            contenido = "La trazabilidad permite saber de dónde viene cada ingrediente.",
                            esVerdadero = true
                        ),
                        EntityTrueOrFalseQuestion(
                            contenido = "Selvawa es solo una marca comercial, no trabaja con comunidades.",
                            esVerdadero = false
                        )
                    )
                )
            }

            // Preguntas con imagen
            val preguntasImg = db.imageQuestionDAO().obtenerTodas()
            if (preguntasImg.isEmpty()) {
                db.imageQuestionDAO().insertar(
                    listOf(
                        EntityImageQuestion(
                            contenido = "¿Qué representa este jaguar en el empaque?",
                            imageUrl = "img_jaguar", // Asegúrate que esté en drawable
                            respuesta1 = "Fuerza y protección de la Amazonía",
                            respuesta2 = "Ingrediente principal",
                            respuesta3 = "Espíritu del mar",
                            respuesta4 = "Cazador enemigo",
                            respuestaCorrecta = 1
                        ),
                        EntityImageQuestion(
                            contenido = "¿Para qué se usa tradicionalmente el sacha cilantro?",
                            imageUrl = "img_sachaculantro",
                            respuesta1 = "Dar color a las comidas",
                            respuesta2 = "Perfumar aceites",
                            respuesta3 = "Saborizante en sopas y guisos",
                            respuesta4 = "Aumentar picante",
                            respuestaCorrecta = 3
                        ),
                        EntityImageQuestion(
                            contenido = "¿Qué ingrediente amazónico aparece junto al ají en el empaque?",
                            imageUrl = "img_aji_con_guayusa",
                            respuesta1 = "Camote",
                            respuesta2 = "Guayusa",
                            respuesta3 = "Limón",
                            respuesta4 = "Tomate",
                            respuestaCorrecta = 2
                        ),
                        EntityImageQuestion(
                            contenido = "¿Cuáles son los tres principales pasos de trazabilidad ilustrados?",
                            imageUrl = "img_trazabilidad",
                            respuesta1 = "Cultivo, preparación y envasado",
                            respuesta2 = "Compra, análisis y diseño",
                            respuesta3 = "Cosecha, transporte y venta",
                            respuesta4 = "Recolección, almacenamiento y facturación",
                            respuestaCorrecta = 1
                        ),
                        EntityImageQuestion(
                            contenido = "¿Qué valor está representado en los íconos del empaque?",
                            imageUrl = "img_valores",
                            respuesta1 = "Respeto por la biodiversidad",
                            respuesta2 = "Exclusividad gourmet",
                            respuesta3 = "Exportación rápida",
                            respuesta4 = "Alta tecnología",
                            respuestaCorrecta = 1
                        )
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
        avatarOsoHormiguero = findViewById(R.id.avatarOsoHormiguero)
        avatarJaguar = findViewById(R.id.avatarJaguar)
        avatarTucan = findViewById(R.id.avatarTucan)
        avatarPerezoso = findViewById(R.id.avatarPerezoso)
    }

    private fun initEventListeners() {
        avatarOsoHormiguero.setOnClickListener { seleccionarAvatar("img_player1") }
        avatarJaguar.setOnClickListener { seleccionarAvatar("img_player2") }
        avatarTucan.setOnClickListener { seleccionarAvatar("img_player3") }
        avatarPerezoso.setOnClickListener { seleccionarAvatar("img_player4") }

        buttonAJugar.setOnClickListener {
            if (avatarSeleccionado == null) {
                Toast.makeText(this, "Selecciona un avatar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            avataresSeleccionados.add(avatarSeleccionado!!)
            deshabilitarAvatar(avatarSeleccionado!!)
            if (jugadorActual < totalJugadores) {
                jugadorActual++
                avatarSeleccionado = null
                actualizarTitulo()
                resetAlphaAvatares()
            } else {
                // Solo aquí lanzas la actividad de preguntas
                lifecycleScope.launch {
                    val multiples = db.multipleChoiceQuestionDAO().obtenerTodas().shuffled().take(3)
                    val verdaderoFalso = db.trueFalseQuestionDAO().obtenerTodas().shuffled().take(3)
                    val imagenes = db.imageQuestionDAO().obtenerTodas().shuffled().take(4)
                    val preguntas = (multiples + verdaderoFalso + imagenes).shuffled()
                    val intent = Intent(this@ChooseAvatar, GameQuestions::class.java)
                    intent.putParcelableArrayListExtra("preguntas", ArrayList(preguntas))
                    intent.putStringArrayListExtra("avatares", ArrayList(avataresSeleccionados))
                    intent.putExtra("puntos", 0)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun seleccionarAvatar(nombre: String) {
        avatarSeleccionado = nombre
        avatarOsoHormiguero.alpha = if (nombre == "img_player1") 1f else 0.5f
        avatarJaguar.alpha = if (nombre == "img_player2") 1f else 0.5f
        avatarTucan.alpha = if (nombre == "img_player3") 1f else 0.5f
        avatarPerezoso.alpha = if (nombre == "img_player4") 1f else 0.5f
    }

    private fun deshabilitarAvatar(nombre: String) {
        when (nombre) {
            "img_player1" -> avatarOsoHormiguero.isEnabled = false
            "img_player2" -> avatarJaguar.isEnabled = false
            "img_player3" -> avatarTucan.isEnabled = false
            "img_player4" -> avatarPerezoso.isEnabled = false
        }
    }

    private fun resetAlphaAvatares() {
        avatarOsoHormiguero.alpha = if (avatarOsoHormiguero.isEnabled) 1f else 0.5f
        avatarJaguar.alpha = if (avatarJaguar.isEnabled) 1f else 0.5f
        avatarTucan.alpha = if (avatarTucan.isEnabled) 1f else 0.5f
        avatarPerezoso.alpha = if (avatarPerezoso.isEnabled) 1f else 0.5f
    }

    private fun actualizarTitulo() {
        val textViewTitulo = findViewById<TextView>(R.id.textViewTitulo)
        textViewTitulo.text = "Jugador $jugadorActual: Elige un avatar"
    }
}
