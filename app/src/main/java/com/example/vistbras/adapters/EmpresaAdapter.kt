package com.example.vistbras.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.vistbras.models.Empresa

class EmpresaArrayAdapter(
    context: Context,
    empresas: List<Empresa>
) : ArrayAdapter<Empresa>(context, android.R.layout.simple_dropdown_item_1line, empresas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val empresa = getItem(position)
        view.findViewById<TextView>(android.R.id.text1).text = empresa?.nome
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val empresa = getItem(position)
        view.findViewById<TextView>(android.R.id.text1).text = empresa?.nome
        return view
    }
}
