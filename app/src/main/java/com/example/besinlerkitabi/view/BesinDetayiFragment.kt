package com.example.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.besinlerkitabi.view.BesinDetayiFragmentArgs
import com.example.besinlerkitabi.R
import com.example.besinlerkitabi.utils.createPlaceHolder
import com.example.besinlerkitabi.utils.gorselIndir
import com.example.besinlerkitabi.viewmodel.BesinDetayiViewModel
import kotlinx.android.synthetic.main.fragment_besin_detayi.*


class BesinDetayiFragment : Fragment() {
    private lateinit var viewModel : BesinDetayiViewModel

    private var besin_id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_detayi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besin_id = BesinDetayiFragmentArgs.fromBundle(it).besinId
            System.out.println(besin_id)



        }

        viewModel = ViewModelProviders.of(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(besin_id)

        observeData()
    }
    fun observeData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin ->
            besin?.let {
                besinismi.text = besin.besinIsim
                besinkalorisi.text = it.besinKalori
                besincarb.text = it.besinKarbonhidrat
                besinprot.text = it.besinProtein
                besinyag.text = it.BesinYag
                context?.let {
                    besin.BesinGörsel?.let { it1 -> imgBesin.gorselIndir(createPlaceHolder(it), it1) }
                }


            }
        })
    }


}