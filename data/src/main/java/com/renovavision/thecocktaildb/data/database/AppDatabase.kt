package com.renovavision.thecocktaildb.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.renovavision.thecocktaildb.data.database.dao.CategoriesDao
import com.renovavision.thecocktaildb.data.database.dao.CocktailsDao
import com.renovavision.thecocktaildb.data.database.dao.IngredientsDao
import com.renovavision.thecocktaildb.data.entities.CategoryEntity
import com.renovavision.thecocktaildb.data.entities.CocktailDetailsEntity
import com.renovavision.thecocktaildb.data.entities.CocktailEntity
import com.renovavision.thecocktaildb.data.entities.IngredientEntity

@Database(
    entities = [
        CategoryEntity::class,
        IngredientEntity::class,
        CocktailEntity::class,
        CocktailDetailsEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCategoriesDao(): CategoriesDao

    abstract fun getIngredientsDao(): IngredientsDao

    abstract fun getCocktailsDao(): CocktailsDao

    companion object {
        const val DB_NAME = "cocktails_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}