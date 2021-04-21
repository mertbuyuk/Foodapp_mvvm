package com.example.besinlerkitabi.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.ViewInfo
import com.example.besinlerkitabi.R
import com.example.besinlerkitabi.adapters.BesinRecyclerAdapter
import com.example.besinlerkitabi.viewmodel.BeslinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_listesi.*


class BesinListesiFragment : Fragment() {
    private lateinit var viewModel : BeslinListesiViewModel
    private  val besinRecyclerAdapter = BesinRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(BeslinListesiViewModel::class.java)
        viewModel.refreshData()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = besinRecyclerAdapter
        //-------------------------------//
        swipelayout.setOnRefreshListener {
            BesinLoading.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            txtbesinerrorMessage.visibility =  View.GONE

            viewModel.refreshFromInternet()
            swipelayout.isRefreshing = false
        }
        //---------------------------------//
        observeLiveData() /*değişen değerleri kontrol ediyoruz bu methodta method içinde ki değerler view
        //modelden sağlanır*/

       /* btn_To_besindetayı.setOnClickListener{
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(3)
            Navigation.findNavController(it).navigate(action)
        }*/
    }



    //durumları gözlemek ve onun için görüntü döndürmek(hata varsa hata ekranı döndür gibi bu değerleri de viewmodelden alıyoruz)
    fun observeLiveData(){
        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let {
                recyclerView.visibility = View.VISIBLE
                besinRecyclerAdapter.besinListeGuncelle(besinler)
            }
        })
        viewModel.besinHataMesaj.observe(viewLifecycleOwner, Observer { hata ->
            hata?.let {
                if (it){
                    System.out.println("sdasd")
                    recyclerView.visibility = View.GONE
                    txtbesinerrorMessage.visibility =  View.VISIBLE



                }
                else{
                    txtbesinerrorMessage.visibility =  View.GONE
                }
            }
        })
        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { yukleniyor ->
            yukleniyor?.let {
                if(it){
                    BesinLoading.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    txtbesinerrorMessage.visibility =  View.GONE

                }
                else{
                    BesinLoading.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }



}