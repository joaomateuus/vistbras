package com.example.vistbras.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vistbras.R
import com.example.vistbras.models.Denuncia
import com.example.vistbras.models.ExtintorItemResponse

class DenunciaItemAdapter(private val onItemClicked: (Denuncia) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Denuncia> = ArrayList()

    fun setDenuncias(denuncias: List<Denuncia>?) {
        if (denuncias == null) {
            return
        }

        this.items = denuncias
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DenunciaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.res_item_denuncia, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DenunciaViewHolder -> {
                holder.bind(items[position], onItemClicked)
            }
        }
    }

    class DenunciaViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val local: TextView = itemView.findViewById(R.id.localText)
        private val data: TextView = itemView.findViewById(R.id.dataText)
        private val protocolo: TextView = itemView.findViewById(R.id.protocoloText)
        private val usuario: TextView = itemView.findViewById(R.id.usuarioText)

        fun bind(denuncia: Denuncia, onItemClicked: (Denuncia) -> Unit) {
            local.text = denuncia.local_ocorrencia
            data.text = denuncia.dt_denuncia
            protocolo.text = denuncia.numero_protocolo
            usuario.text = denuncia.nome_usuario

            itemView.setOnClickListener {
                onItemClicked(denuncia)
            }
        }


    }

}