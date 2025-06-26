package com.smartPackaging.selvawa.landingPage

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smartPackaging.selvawa.game.R

class LandingPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_landing_page)
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
        selectBottomBarItem(0)
    }

    private fun initUIComponents() {
        // Initialize UI components here
    }

    private fun initEventListeners() {
        // Set up event listeners for UI components here
    }

    private fun selectBottomBarItem(selectedIndex: Int) {
        val iconIds = listOf(
            R.id.imageViewInicio,
            R.id.imageViewProductos,
            R.id.imageViewJuego,
            R.id.imageViewVideoInteractivo,
            R.id.imageViewPerfil
        )
        val textIds = listOf(
            R.id.textViewInicio,
            R.id.textViewProductos,
            R.id.textViewJuego,
            R.id.textViewVideoInteractivo,
            R.id.textViewPerfil
        )
        val selectedColor = getColor(R.color.primary_provisional_selected)
        val defaultColor = getColor(R.color.white)

        for (i in iconIds.indices) {
            val icon = findViewById<ImageView>(iconIds[i])
            val text = findViewById<TextView>(textIds[i])
            if (i == selectedIndex) {
                icon.setColorFilter(selectedColor)
                text.setTextColor(selectedColor)
            } else {
                icon.setColorFilter(defaultColor)
                text.setTextColor(defaultColor)
            }
        }
    }

}