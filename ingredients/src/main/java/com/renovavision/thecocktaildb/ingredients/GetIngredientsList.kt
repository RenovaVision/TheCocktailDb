package com.renovavision.thecocktaildb.ingredients

import com.renovavision.thecocktaildb.network.data.repo.IngredientsRepo
import javax.inject.Inject

class GetIngredientsList @Inject constructor (private val ingredientsRepo: IngredientsRepo) {

    suspend fun invoke() = ingredientsRepo.loadIngredients()
}