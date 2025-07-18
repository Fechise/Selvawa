package com.smartPackaging.selvawa.game.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "multiple_choice_question")
data class EntityMultipleChoiceQuestion (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contenido: String,
    val respuesta1: String,
    val respuesta2: String,
    val respuesta3: String,
    val respuesta4: String,
    val respuestaCorrecta: Int
) : Parcelable