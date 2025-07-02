package com.smartPackaging.selvawa.products

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.smartPackaging.selvawa.game.PresentationGame
import com.smartPackaging.selvawa.game.R
import com.smartPackaging.selvawa.interactiveVideo.PresentationInteractiveVideo
import com.smartPackaging.selvawa.landingPage.LandingPage
import com.smartPackaging.selvawa.profile.Profile

class Products : AppCompatActivity() {

    private lateinit var linearLayoutHome : LinearLayout
    private lateinit var linearLayoutProductos : LinearLayout
    private lateinit var linearLayoutJuego : LinearLayout
    private lateinit var linearLayoutVideoInteractivo : LinearLayout
    private lateinit var linearLayoutPerfil : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_products)
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
        linearLayoutHome = findViewById(R.id.linearLayoutHome)
        linearLayoutProductos = findViewById(R.id.linearLayoutProductos)
        linearLayoutJuego = findViewById(R.id.linearLayoutJuego)
        linearLayoutVideoInteractivo = findViewById(R.id.linearLayoutVideoInteractivo)
        linearLayoutPerfil = findViewById(R.id.linearLayoutPerfil)
    }

    private fun initEventListeners() {
        linearLayoutHome.setOnClickListener {
            selectBottomBarItem(0)
            intent = intent.setClass(this, LandingPage::class.java)
            startActivity(intent)
        }
//        linearLayoutProductos.setOnClickListener {
//            selectBottomBarItem(1)
//            intent = intent.setClass(this, Products::class.java)
//            startActivity(intent)
//        }
        linearLayoutJuego.setOnClickListener {
            selectBottomBarItem(2)
            intent = intent.setClass(this, PresentationGame::class.java)
            startActivity(intent)
            finish()
        }
        linearLayoutVideoInteractivo.setOnClickListener {
            selectBottomBarItem(3)
            intent = intent.setClass(this, PresentationInteractiveVideo::class.java)
            startActivity(intent)
        }
        linearLayoutPerfil.setOnClickListener {
            selectBottomBarItem(4)
            intent = intent.setClass(this, Profile::class.java)
            startActivity(intent)
        }
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