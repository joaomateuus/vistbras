package com.example.vistbras.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.vistbras.R
import com.example.vistbras.models.ExtintorItemResponse
import com.example.vistbras.models.Extintores

class ExtintorItemAdapter(private val onItemClicked: (ExtintorItemResponse) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: Extintores = Extintores()

    fun setExtintores(extintores: Extintores?) {
        if (extintores == null) {
            return
        }
        this.items = extintores
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExtintorViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.res_item_extintor, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ExtintorViewHolder -> {
                holder.bind(items[position], onItemClicked)
            }
        }
    }

    class ExtintorViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val codigo: TextView = itemView.findViewById(R.id.codigoText)
        private val tipo: TextView = itemView.findViewById(R.id.tipoText)
        private val tamanho: TextView = itemView.findViewById(R.id.tamanhoText)
        private val empresa: TextView = itemView.findViewById(R.id.empresaText)
        private val local: TextView = itemView.findViewById(R.id.localText)
        private val dtValidade: TextView = itemView.findViewById(R.id.validadeText)
        private val qrcode: ImageView = itemView.findViewById(R.id.qrcode)

        fun bind(extintor: ExtintorItemResponse, onItemClicked: (ExtintorItemResponse) -> Unit) {
            codigo.text = extintor.codigo
            tipo.text = extintor.tipo
            tamanho.text = extintor.tamanho
            empresa.text = extintor.empresa_nome
            local.text = extintor.local
            dtValidade.text = extintor.data_validade

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(extintor.qrcode?.image)
                .into(qrcode)

            itemView.setOnClickListener {
                onItemClicked(extintor)
            }
        }

    }

}