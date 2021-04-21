package com.example.besinlerkitabi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class BesinModel(
        @ColumnInfo(name = "isim")
        @SerializedName("isim")
        val besinIsim : String? ,

        @ColumnInfo(name = "kalori")
        @SerializedName("kalori")
        val besinKalori : String?,

        @ColumnInfo(name = "karbonhidrat")
        @SerializedName("karbonhidrat")
        val besinKarbonhidrat : String? ,

        @ColumnInfo(name = "protein")
        @SerializedName("protein")
        val besinProtein : String?,

        @ColumnInfo(name = "yag")
        @SerializedName("yag")
        val BesinYag : String? ,

        @ColumnInfo(name = "gorsel")
        @SerializedName("gorsel")
        val BesinGörsel : String?
        ) {

        @PrimaryKey(autoGenerate = true)//otomatik id ataması
        var uuid : Int  = 0

}