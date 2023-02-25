package com.apmolokanova.networking

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class CharactersViewModel : ViewModel() {
    private val repository: Repository = Repository()
    val charactersList = repository.charactersList

    var filtration_status : MutableLiveData<Int?> = MutableLiveData<Int?>(null)
    var filtration_gender : MutableLiveData<Int?> = MutableLiveData<Int?>(null)

    suspend fun getCharacterImage(character: Character): Drawable? = withContext(Dispatchers.IO) {
        return@withContext repository.provideCharacterImage(character)
    }
}