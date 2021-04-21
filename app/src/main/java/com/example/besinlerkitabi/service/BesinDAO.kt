package com.example.besinlerkitabi.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.besinlerkitabi.model.BesinModel

@Dao
interface BesinDAO {
    //Data Acces Object
    //datalara ulaştığımız yer

    @Insert
    suspend fun insertAll(vararg besin : BesinModel) : List<Long>

    //Room -> INSERT INTO
    //birden fazla içeriye parametre vermeyi sğlar

    @Query("SELECT * FROM BESINMODEL")
    suspend fun getAllBesin() : List<BesinModel>

    @Query("SELECT * FROM BESINMODEL WHERE uuid = :besinID ")
    suspend fun getBesin(besinID : Int) : BesinModel

    @Query("DELETE FROM BESINMODEL")
    suspend fun deleteAllBesin()

    @Query("DELETE FROM BESINMODEL WHERE uuid = :besinID")
    suspend fun deleteChoosenBesin(besinID : Int)

}