package com.apmolokanova.networking

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository {
    private val networkModule: NetworkModule = NetworkModule();

    private val _charactersList: MutableLiveData<ArrayList<Character>> = MutableLiveData(ArrayList())
    val charactersList = _charactersList

    private val charImgCache: MutableMap<Int, Drawable?> = mutableMapOf()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                _charactersList.value = networkModule.getCharactersList()
            }
        }
    }

    suspend fun provideCharacterImage(character: Character): Drawable? = withContext(Dispatchers.IO) {
        if (!charImgCache.containsKey(character.id)) {
            charImgCache[character.id!!] = networkModule.loadImageFromWebOperations(character.image!!)
        }
        return@withContext charImgCache[character.id]
    }


    suspend fun provideImageByLink(link: String): Drawable? = withContext(Dispatchers.IO) {
        return@withContext networkModule.loadImageFromWebOperations(link)
    }

}