package com.renovavision.thecocktaildb.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renovavision.thecocktaildb.data.entities.DrinksCategory.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<Category>)

    @Query("DELETE FROM ${Category.TABLE_NAME}")
    fun deleteAllCategories()

    @Query("SELECT * FROM ${Category.TABLE_NAME}")
    fun getAllCategories(): Flow<List<Category>>
}