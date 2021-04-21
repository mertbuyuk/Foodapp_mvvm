package com.example.besinlerkitabi.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.besinlerkitabi.R
import com.example.besinlerkitabi.model.BesinModel
import com.example.besinlerkitabi.utils.createPlaceHolder
import com.example.besinlerkitabi.utils.gorselIndir
import com.example.besinlerkitabi.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.besin_recycler_row.view.*

class BesinRecyclerAdapter(val  besinListesi : ArrayList<BesinModel>) : RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>() {
    class BesinViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.besin_recycler_row,parent,false)
        return  BesinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  besinListesi.size
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {
        holder.itemView.isim.text =   besinListesi.get(position).besinIsim
        holder.itemView.kalori.text = besinListesi.get(position).besinKalori

        //recycler tıklanınca hareket
        holder.itemView.setOnClickListener{
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(besinListesi.get(position).uuid)
            Navigation.findNavController(it).navigate(action)

        }

        //görsel
        besinListesi.get(position)
                .BesinGörsel?.let {
                    holder.itemView.imgview.gorselIndir(createPlaceHolder(holder.itemView.context), it) }
    }
    fun besinListeGuncelle(yeniBesinListesi : List<BesinModel>){
        besinListesi.clear()
        besinListesi.addAll(yeniBesinListesi)
        notifyDataSetChanged()

    }

}