package com.renovavision.thecocktaildb.utils

interface ViewEvent

interface Dispatchable
interface Event : Dispatchable
typealias Dispatch = (dispatchable: Dispatchable) -> Unit