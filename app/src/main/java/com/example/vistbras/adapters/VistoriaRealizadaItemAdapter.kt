package com.example.vistbras.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vistbras.R
import com.example.vistbras.models.VistoriaRealizada

class VistoriaRealizadaItemAdapter(private val onItemClicked: (VistoriaRealizada) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<VistoriaRealizada> = ArrayList()

    fun setVistoriasRealizadas(vistorias: List<VistoriaRealizada>?) {
        if (vistorias == null) {
            return
        }

        this.items = vistorias
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VistoriaRealizadaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.res_item_vistoria_realizada, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VistoriaRealizadaViewHolder -> {
                holder.bind(items[position], onItemClicked)
            }
        }
    }

    class VistoriaRealizadaViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val data: TextView = itemView.findViewById(R.id.dataText)
        private val fiscal: TextView = itemView.findViewById(R.id.fiscalText)
        private val extintor: TextView = itemView.findViewById(R.id.extintorText)
        private val protocol: TextView = itemView.findViewById(R.id.protocoloText)

        fun bind(vistoriaRealizada: VistoriaRealizada, onItemClicked: (VistoriaRealizada) -> Unit) {
            data.text = vistoriaRealizada.data_realizada
            fiscal.text = vistoriaRealizada.fiscal_nome
            extintor.text = vistoriaRealizada.extintor_codigo
            protocol.text = vistoriaRealizada.numero_protocolo

            itemView.setOnClickListener {
                onItemClicked(vistoriaRealizada)
            }
        }

    }


}