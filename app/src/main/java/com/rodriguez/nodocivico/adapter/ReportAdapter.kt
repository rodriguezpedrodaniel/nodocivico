package com.rodriguez.nodocivico.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rodriguez.nodocivico.R
import com.rodriguez.nodocivico.data.local.entity.Reporte

class ReportAdapter(
    private var lista: List<Reporte>,
    private val onDelete: (Reporte) -> Unit
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    class ReportViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView) {

        val txtTitulo: TextView =
            itemView.findViewById(R.id.txtTitulo)

        val txtDescripcion: TextView =
            itemView.findViewById(R.id.txtDescripcion)

        val txtCategoria: TextView =
            itemView.findViewById(R.id.txtCategoria)

        val btnEliminar: Button =
            itemView.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReportViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_report, parent, false)

        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(
        holder: ReportViewHolder,
        position: Int
    ) {

        val reporte = lista[position]

        holder.txtTitulo.text = reporte.titulo
        holder.txtDescripcion.text = reporte.descripcion
        holder.txtCategoria.text = reporte.categoria

        holder.btnEliminar.setOnClickListener {

            onDelete(reporte)
        }
    }

    fun setData(nuevaLista: List<Reporte>) {

        lista = nuevaLista
        notifyDataSetChanged()
    }
}