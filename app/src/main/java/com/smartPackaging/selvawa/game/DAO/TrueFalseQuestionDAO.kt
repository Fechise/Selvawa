package com.smartPackaging.selvawa.game.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.smartPackaging.selvawa.game.entity.EntityTrueFalseQuestion

@Dao
interface TrueFalseQuestionDAO {
    @Query("SELECT * FROM true_false_question")
    suspend fun obtenerTodas(): List<EntityTrueFalseQuestion>

    @Insert
    suspend fun insertar(trueFalseQuestion: EntityTrueFalseQuestion)
}