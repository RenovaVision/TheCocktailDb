package com.renovavision.thecocktaildb.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renovavision.thecocktaildb.data.entities.CocktailDetailsEntity
import com.renovavision.thecocktaildb.data.entities.CocktailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktails(categories: List<CocktailEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktailDetails(cocktail: List<CocktailDetailsEntity>)

    @Query("SELECT * FROM ${CocktailEntity.TABLE_NAME} WHERE category = :param OR ingredient = :param")
    fun getCocktails(param: String): Flow<List<CocktailEntity>>

    @Query("SELECT * FROM ${CocktailDetailsEntity.TABLE_NAME} WHERE idDrink = :id")
    fun getCocktailDetails(id: Int): Flow<CocktailDetailsEntity>

    @Query("DELETE FROM ${CocktailEntity.TABLE_NAME}")
    fun deleteAllCocktails()

    @Query("DELETE FROM ${CocktailDetailsEntity.TABLE_NAME}")
    fun deleteAllCocktailDetails()
}