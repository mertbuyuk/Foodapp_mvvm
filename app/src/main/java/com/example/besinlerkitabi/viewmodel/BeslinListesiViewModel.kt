package com.example.besinlerkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.besinlerkitabi.model.BesinModel
import com.example.besinlerkitabi.service.BesinAPIService
import com.example.besinlerkitabi.service.BesinDatabase
import com.example.besinlerkitabi.utils.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BeslinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<BesinModel>>()
    val besinHataMesaj = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private var guncellemeZamani = 10*60*1000*1000*1000L

    private val apiServis = BesinAPIService()
    private val disposable = CompositeDisposable()//tek seferlik. İşlemleri yaptıktan sonra kurtulabiliyorsun

    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    fun refreshFromInternet(){
        verileriInternettenAl()
    }

    fun refreshData() {

        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()

        if(kaydedilmeZamani!=null && kaydedilmeZamani != 0L &&  System.nanoTime() - kaydedilmeZamani < guncellemeZamani){
            //sqlitedan al
            verilerSQLitedanAl()
        }
        else{
            verileriInternettenAl()
        }

    }

    private fun verilerSQLitedanAl(){
        launch {
            val dao = BesinDatabase(getApplication()).besinDAO()
            val BesinListesi =  dao.getAllBesin()

            besinleriGoster(BesinListesi)
            Toast.makeText(getApplication(),"Roomdan",Toast.LENGTH_SHORT).show()
        }
    }



    private fun verileriInternettenAl() {
        besinYukleniyor.value = true

        disposable.add(
                apiServis.getData()
                        .subscribeOn(Schedulers.newThread())//yeni threadde kayıt ol ve işlemleri arka planda yap
                        .observeOn(AndroidSchedulers.mainThread())//main threadde değişiklikleri farket
                        .subscribeWith(object : DisposableSingleObserver<List<BesinModel>>() {
                            //İnti yenileyelim oalyları farkedelim kapat
                            override fun onSuccess(t: List<BesinModel>) {
                                sqliteSakla(t)
                            }

                            override fun onError(e: Throwable) {
                                besinHataMesaj.value = true
                                besinYukleniyor.value = false
                                e.printStackTrace()
                            }

                        })
        )
        Toast.makeText(getApplication(),"İnternetten aldık",Toast.LENGTH_SHORT).show()


    }

    private fun besinleriGoster(besinlerListesi: List<BesinModel>) {
        besinYukleniyor.value = false
        besinler.value = besinlerListesi
        besinHataMesaj.value = false
    }

    private fun sqliteSakla(besinlerListesi: List<BesinModel>) {
        launch {
            val dao = BesinDatabase(getApplication()).besinDAO()
            dao.deleteAllBesin()
            val uuidListesi = dao.insertAll(*besinlerListesi.toTypedArray())

            var i = 0
            while (i < besinlerListesi.size) {
                besinlerListesi[i].uuid = uuidListesi[i].toInt()
                i++
            }
            besinleriGoster(besinlerListesi)


        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())

    }
}