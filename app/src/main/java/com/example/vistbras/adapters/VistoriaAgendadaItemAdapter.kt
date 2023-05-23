package com.example.vistbras.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vistbras.R
import com.example.vistbras.models.VistoriaAgendada

class VistoriaAgendadaItemAdapter(private val onItemClicked: (VistoriaAgendada) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<VistoriaAgendada> = ArrayList()

    fun setVistoriasAgendadas(vistoriasAgend: List<VistoriaAgendada>?) {
        if (vistoriasAgend == null) {
            return
        }

        this.items = vistoriasAgend
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VistoriaAgendadaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.res_item_vistoria_agendada, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VistoriaAgendadaViewHolder -> {
                holder.bind(items[position], onItemClicked)
            }
        }
    }

    class VistoriaAgendadaViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val empresa: TextView = itemView.findViewById(R.id.empresaText)
        private val protocolo: TextView = itemView.findViewById(R.id.protocoloText)
        private val dataAgendada: TextView = itemView.findViewById(R.id.dtAgendadaText)
        private val contato: TextView = itemView.findViewById(R.id.contatoText)

        fun bind(vistoriaAgend: VistoriaAgendada, onItemClicked: (VistoriaAgendada) -> Unit) {
            empresa.text = vistoriaAgend.empresa_agendada
            protocolo.text = vistoriaAgend.protocolo_agendamento
            dataAgendada.text = vistoriaAgend.data_agendada
            contato.text = vistoriaAgend.contato

            itemView.setOnClickListener {
                onItemClicked(vistoriaAgend)
            }
        }

    }


}