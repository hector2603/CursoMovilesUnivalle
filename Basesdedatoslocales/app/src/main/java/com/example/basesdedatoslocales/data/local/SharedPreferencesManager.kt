package com.example.basesdedatoslocales.data.local

import android.content.Context

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)

    fun setFavorite(contactId: Int, isFavorite: Boolean) {
        sharedPreferences.edit().putBoolean(contactId.toString(), isFavorite).apply()
    }

    fun isFavorite(contactId: Int): Boolean {
        return sharedPreferences.getBoolean(contactId.toString(), false)
    }
}
