package com.renovavision.thecocktaildb.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renovavision.thecocktaildb.data.entities.DrinksIngredient.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIngredients(ingredients: List<Ingredient>)

    @Query("DELETE FROM ${Ingredient.TABLE_NAME}")
    fun deleteAllIngredients()

    @Query("SELECT * FROM ${Ingredient.TABLE_NAME}")
    fun getAllIngredients(): Flow<List<Ingredient>>
}