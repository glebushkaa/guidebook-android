package com.gm.ai.guidebook.domain.repository

import com.gm.ai.guidebook.model.Guide

/**
 * Created by gle.bushkaa email(gleb.mokryy@gmail.com) on 10/30/2023
 */

interface FavoritesRepository {
    suspend fun searchFavorites(query: String): List<Guide>

    suspend fun addFavorite(id: String)

    suspend fun removeFavorite(id: String)
}
