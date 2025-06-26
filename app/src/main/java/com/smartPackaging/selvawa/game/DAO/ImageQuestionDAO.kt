package com.smartPackaging.selvawa.game.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.smartPackaging.selvawa.game.entity.EntityImageQuestion

@Dao
interface ImageQuestionDAO {
    @Query("SELECT * FROM image_question")
    suspend fun obtenerTodas(): List<EntityImageQuestion>

    @Insert
    suspend fun insertar(imageQuestion: EntityImageQuestion)
}