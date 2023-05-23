import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.vistbras.models.ExtintorItemResponse

class ExtintorArrayAdapter(
    context: Context,
    extintores: List<ExtintorItemResponse>
) : ArrayAdapter<ExtintorItemResponse>(context, 0, extintores) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        }

        val extintor = getItem(position)
        val codigoTextView = view?.findViewById<TextView>(android.R.id.text1)
        val empresaNomeTextView = view?.findViewById<TextView>(android.R.id.text2)

        codigoTextView?.text = extintor?.codigo
        empresaNomeTextView?.text = extintor?.empresa_nome

        return view!!
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        }

        val extintor = getItem(position)
        val codigoTextView = view?.findViewById<TextView>(android.R.id.text1)
        val empresaNomeTextView = view?.findViewById<TextView>(android.R.id.text2)

        codigoTextView?.text = extintor?.codigo
        empresaNomeTextView?.text = extintor?.empresa_nome

        return view!!
    }
}
