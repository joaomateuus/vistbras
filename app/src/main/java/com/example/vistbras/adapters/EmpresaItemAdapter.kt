package com.example.vistbras.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vistbras.R
import com.example.vistbras.models.Empresa

class EmpresaItemAdapter(private val onItemClicked: (Empresa) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Empresa> = ArrayList()

    fun setEmpresas(empresas: List<Empresa>?) {
        if (empresas == null) {
            return
        }
        this.items = empresas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EmpresaViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.res_item_empresa, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EmpresaViewHolder -> {
                holder.bind(items[position], onItemClicked)
            }
        }
    }

    class EmpresaViewHolder constructor(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val nome: TextView = itemView.findViewById(R.id.nomeText)
        private val cnpj: TextView = itemView.findViewById(R.id.cnpjText)
        private val contato: TextView = itemView.findViewById(R.id.contatoText)
        private val area: TextView = itemView.findViewById(R.id.areaText)
        private val endereco: TextView = itemView.findViewById(R.id.enderecoText)

        fun bind(empresa: Empresa, onItemClicked: (Empresa) -> Unit) {
            nome.text = empresa.nome
            cnpj.text = empresa.cnpj
            contato.text = empresa.contato
            area.text = empresa.area_atuacao
            endereco.text = empresa.endereco


            itemView.setOnClickListener {
                onItemClicked(empresa)
            }
        }
    }
}