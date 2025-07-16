package com.smartPackaging.selvawa.game

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.smartPackaging.selvawa.game.database.AppDatabase
import com.smartPackaging.selvawa.game.entity.EntityImageQuestion
import com.smartPackaging.selvawa.game.entity.EntityMultipleChoiceQuestion
import com.smartPackaging.selvawa.game.entity.EntityTrueOrFalseQuestion
import com.smartPackaging.selvawa.landingPage.LandingPage
import kotlinx.coroutines.launch
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
class PresentationGame : AppCompatActivity() {

    /// Beneficios de ingredientes, principio de aprendizaje colectivo. Aprender sobre la nutrición

    private lateinit var buttonAJugar: Button
    private lateinit var buttonIrAPaginaPrincipal: Button

    private lateinit var imageViewFacebook: ImageView
    private lateinit var imageViewInstagram: ImageView
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
        initUI()
    }

    private fun initUI() {
        initUIComponents()
        initEventListeners()
    }

    private fun initUIComponents() {
        buttonAJugar = findViewById(R.id.buttonAJugar)
        buttonIrAPaginaPrincipal = findViewById(R.id.buttonIrAPaginaPrincipal)
        imageViewFacebook = findViewById(R.id.imageViewFacebook)
        imageViewInstagram = findViewById(R.id.imageViewInstagram)
    }

    private fun initEventListeners() {
        buttonAJugar.setOnClickListener {
            mostrarDialogoNumeroJugadores()
        }
        buttonIrAPaginaPrincipal.setOnClickListener {
            val intent = Intent(this, LandingPage::class.java)
            startActivity(intent)
            finish()
        }
        imageViewFacebook.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.facebook.com/p/Selvawa-100067903841923/?locale=es_LA")
            )
            startActivity(intent)
        }
        imageViewInstagram.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/selvawamazonia/"))
            startActivity(intent)
        }
    }

    private fun mostrarDialogoNumeroJugadores() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_numero_jugadores, null)
        val numberPicker = dialogView.findViewById<NumberPicker>(R.id.numberPickerJugadores)
        numberPicker.minValue = 1
        numberPicker.maxValue = 4
        numberPicker.value = 2

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Aceptar", null)
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.apply {
                setTextColor(ContextCompat.getColor(context, R.color.secondary_cream))
                backgroundTintList = ContextCompat.getColorStateList(context, R.color.primary_provisional_sky)
                typeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
                setOnClickListener {
                    val numeroElegido = numberPicker.value
                    val intent = Intent(context, ChooseAvatar::class.java)
                    intent.putExtra("totalJugadores", numeroElegido)
                    context.startActivity(intent)
                    dialog.dismiss()
                }
            }
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.apply {
                setTextColor(ContextCompat.getColor(context, R.color.secondary_cream))
                backgroundTintList = ContextCompat.getColorStateList(context, R.color.primary_provisional_sky)
                typeface = ResourcesCompat.getFont(context, R.font.poppins_medium)
            }
        }
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_dialog_avatar)
        dialog.show()
    }
}