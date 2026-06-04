package com.rodriguez.nodocivico.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rodriguez.nodocivico.data.local.entity.Reporte
import com.rodriguez.nodocivico.databinding.ItemReportBinding

class ReportAdapter(
    private var lista: List<Reporte>,
    private val onEditClick: (Reporte) -> Unit,
    private val onDeleteClick: (Reporte) -> Unit
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(
        val binding: ItemReportBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val reporte = lista[position]

        with(holder.binding) {
            txtTitulo.text     = reporte.titulo
            txtDescripcion.text = reporte.descripcion
            txtCategoria.text  = reporte.categoria
            txtEstado.text     = "Estado: ${reporte.estado}"

            when (reporte.estado) {
                "Pendiente"  -> txtEstado.setTextColor(Color.parseColor("#F59E0B"))
                "Resuelto"   -> txtEstado.setTextColor(Color.parseColor("#10B981"))
                else         -> txtEstado.setTextColor(Color.parseColor("#DC2626"))
            }

            btnEditar.setOnClickListener  { onEditClick(reporte) }
            btnEliminar.setOnClickListener { onDeleteClick(reporte) }
        }
    }

    override fun getItemCount(): Int = lista.size


    fun actualizarLista(nuevaLista: List<Reporte>) {

        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun getOldListSize(): Int = lista.size
            override fun getNewListSize(): Int = nuevaLista.size

            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
                lista[oldPos].id == nuevaLista[newPos].id

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
                lista[oldPos] == nuevaLista[newPos]
        })
        lista = nuevaLista
        diff.dispatchUpdatesTo(this)
    }
}