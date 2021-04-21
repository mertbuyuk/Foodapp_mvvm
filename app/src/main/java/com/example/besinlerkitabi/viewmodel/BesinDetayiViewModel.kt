package com.example.besinlerkitabi.viewmodel

import android.app.Application
import android.os.ParcelUuid
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.besinlerkitabi.model.BesinModel
import com.example.besinlerkitabi.service.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application) : BaseViewModel(application){

        val besinLiveData = MutableLiveData<BesinModel>()

        fun roomVerisiniAl(uuid: Int) {
            launch {
                val dao = BesinDatabase(getApplication()).besinDAO()
                val besin = dao.getBesin(uuid)
                besinLiveData.value = besin
            }
        }






    }
