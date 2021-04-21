package com.example.besinlerkitabi.service

import com.example.besinlerkitabi.model.BesinModel
import io.reactivex.Single
import retrofit2.http.GET

interface BesinApi {

    //  https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesin() : Single<List<BesinModel>>




}