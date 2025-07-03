package com.smartPackaging.selvawa.game.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.smartPackaging.selvawa.game.entity.EntityTrueOrFalseQuestion

@Dao
interface TrueFalseOrQuestionDAO {
    @Query("SELECT * FROM true_false_question")
    suspend fun obtenerTodas(): List<EntityTrueOrFalseQuestion>

    @Insert
    suspend fun insertar(trueFalseQuestion: EntityTrueOrFalseQuestion)

    @Insert
    suspend fun insertar(trueFalseQuestions: List<EntityTrueOrFalseQuestion>)
}