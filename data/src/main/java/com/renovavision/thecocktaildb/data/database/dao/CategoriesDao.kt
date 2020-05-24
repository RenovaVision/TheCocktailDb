package com.renovavision.thecocktaildb.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.renovavision.thecocktaildb.data.entities.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategories(categories: List<CategoryEntity>)

    @Query("DELETE FROM ${CategoryEntity.TABLE_NAME}")
    fun deleteAllCategories()

    @Query("SELECT * FROM ${CategoryEntity.TABLE_NAME}")
    fun getAllCategories(): Flow<List<CategoryEntity>>
}