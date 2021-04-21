package com.example.besinlerkitabi.service

import com.example.besinlerkitabi.model.BesinModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class BesinAPIService {
    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    // BASE URL - https://raw.githubusercontent.com/

    private val BaseUrl = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(BesinApi::class.java)

    fun getData() : Single<List<BesinModel>>{
        return api.getBesin()

    }



}