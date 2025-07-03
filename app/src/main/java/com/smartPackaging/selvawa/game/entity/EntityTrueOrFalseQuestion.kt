package com.smartPackaging.selvawa.game.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "true_false_question")
data class EntityTrueOrFalseQuestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val contenido: String,
    val esVerdadero: Boolean
) : Parcelable