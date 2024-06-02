import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.enviorapido_pdm.R
import com.example.enviorapido_pdm.Seguimiento
import java.text.SimpleDateFormat
import java.util.*

class SeguimientoAdapter(context: Context, seguimientos: ArrayList<Seguimiento>) :
    ArrayAdapter<Seguimiento>(context, 0, seguimientos) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val viewHolder: ViewHolder

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_seguimiento, parent, false)
            viewHolder = ViewHolder()
            viewHolder.txtFecha = convertView.findViewById(R.id.txtFecha)
            viewHolder.txtEstado = convertView.findViewById(R.id.txtEstado)
            viewHolder.txtUbicacion = convertView.findViewById(R.id.txtUbicacion)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }

        val seguimiento = getItem(position)
        viewHolder.txtFecha?.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(seguimiento?.fechaSeguimiento)
        viewHolder.txtEstado?.text = seguimiento?.estadoSeguimiento
        viewHolder.txtUbicacion?.text = seguimiento?.ubicacionSeguimiento

        return convertView!!
    }

    private class ViewHolder {
        var txtFecha: TextView? = null
        var txtEstado: TextView? = null
        var txtUbicacion: TextView? = null
    }
}
