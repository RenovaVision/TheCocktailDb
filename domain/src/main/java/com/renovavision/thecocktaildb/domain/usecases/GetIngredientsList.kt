package com.renovavision.thecocktaildb.domain.usecases

import com.renovavision.thecocktaildb.domain.repositories.IngredientsRepository
import javax.inject.Inject

class GetIngredientsList @Inject constructor(private val ingredientsRepo: IngredientsRepository) {

    suspend fun invoke() = ingredientsRepo.loadIngredients()
}