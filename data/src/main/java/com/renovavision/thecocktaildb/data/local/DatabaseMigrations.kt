package com.renovavision.thecocktaildb.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.renovavision.thecocktaildb.data.entities.CocktailInfo
import com.renovavision.thecocktaildb.data.entities.DrinksByQuery
import com.renovavision.thecocktaildb.data.entities.DrinksCategory
import com.renovavision.thecocktaildb.data.entities.DrinksIngredient

object DatabaseMigrations {
    const val DB_VERSION = 2

    val MIGRATIONS: Array<Migration>
        get() = arrayOf(
            migration12()
        )

    private fun migration12(): Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE ${DrinksCategory.Category.TABLE_NAME} ADD COLUMN body TEXT")
            database.execSQL("ALTER TABLE ${DrinksIngredient.Ingredient.TABLE_NAME} ADD COLUMN body TEXT")
            database.execSQL("ALTER TABLE ${DrinksByQuery.Drink.TABLE_NAME} ADD COLUMN body TEXT")
            database.execSQL("ALTER TABLE ${CocktailInfo.Cocktail.TABLE_NAME} ADD COLUMN body TEXT")
        }
    }
}
