package com.renovavision.thecocktaildb.cocktails

import com.renovavision.thecocktaildb.network.data.Data
import com.renovavision.thecocktaildb.network.data.repo.CocktailsRepo
import javax.inject.Inject

class CocktailsUseCase @Inject constructor (private val cocktailsRepo: CocktailsRepo) {

    suspend fun invoke(data: Data<*>) = cocktailsRepo.loadData(data)
}