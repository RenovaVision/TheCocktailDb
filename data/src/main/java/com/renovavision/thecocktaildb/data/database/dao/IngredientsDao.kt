package com.renovavision.thecocktaildb.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renovavision.thecocktaildb.data.entities.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(ingredients: List<IngredientEntity>)

    @Query("DELETE FROM ${IngredientEntity.TABLE_NAME}")
    fun deleteAllIngredients()

    @Query("SELECT * FROM ${IngredientEntity.TABLE_NAME}")
    fun getAllIngredients(): Flow<List<IngredientEntity>>
}