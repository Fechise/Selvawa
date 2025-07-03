package com.smartPackaging.selvawa.game.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.smartPackaging.selvawa.game.entity.EntityMultipleChoiceQuestion

@Dao
interface MultipleChoiceQuestionDAO {
    @Query("SELECT * FROM multiple_choice_question")
    suspend fun obtenerTodas(): List<EntityMultipleChoiceQuestion>

    @Insert
    suspend fun insertar(multipleChoiceQuestion: EntityMultipleChoiceQuestion)

    @Insert
    suspend fun insertar(multipleChoiceQuestions: List<EntityMultipleChoiceQuestion>)
}