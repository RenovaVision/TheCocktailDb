package com.renovavision.thecocktaildb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.renovavision.thecocktaildb.data.entities.CocktailInfo.*
import com.renovavision.thecocktaildb.data.entities.DrinksByQuery.*
import com.renovavision.thecocktaildb.data.entities.DrinksCategory.*
import com.renovavision.thecocktaildb.data.entities.DrinksIngredient.*
import com.renovavision.thecocktaildb.data.local.dao.CategoriesDao
import com.renovavision.thecocktaildb.data.local.dao.CocktailsDao
import com.renovavision.thecocktaildb.data.local.dao.IngredientsDao

@Database(
    entities = [
        Category::class,
        Ingredient::class,
        Drink::class,
        Cocktail::class
    ],
    version = DatabaseMigrations.DB_VERSION
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
                )
                    .addMigrations(*DatabaseMigrations.MIGRATIONS)
                    .build()

                INSTANCE = instance
                return instance
            }
        }

    }
}