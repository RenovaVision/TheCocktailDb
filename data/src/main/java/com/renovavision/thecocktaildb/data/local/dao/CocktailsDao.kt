package com.renovavision.thecocktaildb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renovavision.thecocktaildb.data.entities.CocktailInfo.Cocktail
import com.renovavision.thecocktaildb.data.entities.DrinksByQuery.Drink
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDrinks(categories: List<Drink>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktailInfo(cocktail: List<Cocktail>)

    @Query("SELECT * FROM ${Drink.TABLE_NAME} WHERE category = :param OR ingredient = :param")
    fun getDrinks(param: String): Flow<List<Drink>>

    @Query("SELECT * FROM ${Cocktail.TABLE_NAME} WHERE idDrink = :id")
    fun getCocktailInfo(id: Int): Flow<List<Cocktail>>

    @Query("DELETE FROM ${Drink.TABLE_NAME}")
    fun deleteAllDrinks()

    @Query("DELETE FROM ${Cocktail.TABLE_NAME}")
    fun deleteAllCocktailsInfo()
}