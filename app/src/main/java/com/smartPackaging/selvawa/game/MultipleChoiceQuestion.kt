package com.smartPackaging.selvawa.game

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.text.toInt

class MultipleChoiceQuestion : AppCompatActivity() {

    private lateinit var buttonSalirDelJuego: ImageView
    private lateinit var textViewPreguntaContenido: TextView
    private lateinit var gridLayoutOpciones: GridLayout
    private lateinit var imageViewAvatar: ImageView
    private var avatar: String? = null
    private var puntos: Int = 0
    private var contenido: String = ""
    private var opciones: List<String> = listOf()
    private var respuestaCorrecta: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_multiple_choice_question)
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
        val respuesta1 = intent.getStringExtra("respuesta1") ?: ""
        val respuesta2 = intent.getStringExtra("respuesta2") ?: ""
        val respuesta3 = intent.getStringExtra("respuesta3") ?: ""
        val respuesta4 = intent.getStringExtra("respuesta4") ?: ""
        val idxCorrecta = intent.getIntExtra("respuestaCorrecta", 1) - 1
        opciones = listOf(respuesta1, respuesta2, respuesta3, respuesta4)
        respuestaCorrecta = idxCorrecta
        avatar = intent.getStringExtra("avatar")
        puntos = intent.getIntExtra("puntos", 0)
    }

    private fun initUIComponents() {
        buttonSalirDelJuego = findViewById(R.id.buttonSalirDelJuego)
        textViewPreguntaContenido = findViewById(R.id.textViewPreguntaContenido)
        gridLayoutOpciones = findViewById(R.id.gridLayoutOpciones)
        textViewPreguntaContenido.text = contenido
        imageViewAvatar = findViewById(R.id.imageViewAvatar)
        val textViewPuntos = findViewById<TextView>(R.id.textViewPuntos)
        textViewPuntos.text = "Puntos: $puntos"

        avatar?.let {
            val resId = resources.getIdentifier(it, "drawable", packageName)
            if (resId != 0) imageViewAvatar.setImageResource(resId)
        }
        gridLayoutOpciones.removeAllViews()
        for (opcion in opciones) {
            val button = Button(this)
            button.text = opcion
            button.background = ContextCompat.getDrawable(this, R.drawable.bg_button_game_option)
            button.setTextColor(ContextCompat.getColor(this, R.color.white))
            val scale = resources.displayMetrics.density
            val widthPx = (120 * scale + 0.5f).toInt()
            val params = GridLayout.LayoutParams().apply {
                width = widthPx
                height = GridLayout.LayoutParams.WRAP_CONTENT
                setMargins(8, 8, 8, 8)
            }
            button.layoutParams = params
            gridLayoutOpciones.addView(button)
        }
    }

    private fun initEventListeners() {
        for (i in 0 until gridLayoutOpciones.childCount) {
            val button = gridLayoutOpciones.getChildAt(i) as Button
            button.setOnClickListener {
                // Deshabilita todos los botones
                for (j in 0 until gridLayoutOpciones.childCount) {
                    gridLayoutOpciones.getChildAt(j).isEnabled = false
                }
                val esCorrecta = (i == respuestaCorrecta)
                val mensaje = if (esCorrecta) "¡Pica y pasa!" else "¡Tu snack pasa al siguiente!"
                val lottie = if (esCorrecta) R.raw.celebration else R.raw.sad_face
                val dialog = AvisoDialogFragment(mensaje, lottie)
                if (esCorrecta) {
                    button.background = ContextCompat.getDrawable(this, R.drawable.bg_button_game_option_correct)
                    puntos += 2
                } else {
                    button.background = ContextCompat.getDrawable(this, R.drawable.bg_button_game_option_wrong)
                    val buttonCorrecto = gridLayoutOpciones.getChildAt(respuestaCorrecta) as Button
                    buttonCorrecto.setBackgroundColor(ContextCompat.getColor(this, R.color.correct_answer))
                }
                dialog.show(supportFragmentManager, "aviso")
                // Ir a la siguiente pregunta después de un pequeño delay
                button.postDelayed({
                    dialog.dismiss()
                    val preguntas = intent.getParcelableArrayListExtra<android.os.Parcelable>("preguntas")
                    val indice = intent.getIntExtra("indice", 0)
                    if (preguntas != null && indice + 1 < preguntas.size) {
                        val siguiente = preguntas[indice + 1]
                        val nextIntent = when (siguiente) {
                            is com.smartPackaging.selvawa.game.entity.EntityMultipleChoiceQuestion -> {
                                Intent(this, MultipleChoiceQuestion::class.java).apply {
                                    putExtra("contenido", siguiente.contenido)
                                    putExtra("respuesta1", siguiente.respuesta1)
                                    putExtra("respuesta2", siguiente.respuesta2)
                                    putExtra("respuesta3", siguiente.respuesta3)
                                    putExtra("respuesta4", siguiente.respuesta4)
                                    putExtra("respuestaCorrecta", siguiente.respuestaCorrecta)
                                }
                            }
                            is com.smartPackaging.selvawa.game.entity.EntityTrueOrFalseQuestion -> {
                                Intent(this, TrueOrFalseQuestion::class.java).apply {
                                    putExtra("contenido", siguiente.contenido)
                                    putExtra("esVerdadero", siguiente.esVerdadero)
                                }
                            }
                            is com.smartPackaging.selvawa.game.entity.EntityImageQuestion -> {
                                Intent(this, ImageQuestion::class.java).apply {
                                    putExtra("contenido", siguiente.contenido)
                                    putExtra("imageUrl", siguiente.imageUrl)
                                    putExtra("respuesta1", siguiente.respuesta1)
                                    putExtra("respuesta2", siguiente.respuesta2)
                                    putExtra("respuesta3", siguiente.respuesta3)
                                    putExtra("respuesta4", siguiente.respuesta4)
                                    putExtra("respuestaCorrecta", siguiente.respuestaCorrecta)
                                }
                            }
                            else -> null
                        }
                        nextIntent?.putExtra("avatar", avatar)
                        nextIntent?.putExtra("puntos", puntos)
                        nextIntent?.putExtra("indice", indice + 1)
                        nextIntent?.putParcelableArrayListExtra("preguntas", preguntas)
                        startActivity(nextIntent)
                        finish()
                    } else {
                        // Si no hay más preguntas, ir a resultados
                        val resultIntent = Intent(this, GameResults::class.java)
                        resultIntent.putExtra("avatar", avatar)
                        resultIntent.putExtra("puntos", puntos)
                        startActivity(resultIntent)
                        finish()
                    }
                }, 4500)
            }
        }
        buttonSalirDelJuego.setOnClickListener {
            startActivity(Intent(this, PresentationGame::class.java))
            finish()
        }
    }
}