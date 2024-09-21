package com.example.lesson14

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainVM : ViewModel() {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://uselessfacts.jsph.pl/api/v2/facts/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(RandomFactApi::class.java)

    fun getFact(): Observable<List<RandomFact>> {
        return Observable.create{ entity ->
            viewModelScope.launch {
                val toReturn = mutableListOf<RandomFact>()
                for (i in 1..20) {
                    toReturn.add(api.getFacts())
                }
                entity.onNext(toReturn)
                entity.onComplete()
            }
        }
    }
}
