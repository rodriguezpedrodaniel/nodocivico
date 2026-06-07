package com.rodriguez.nodocivico.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.rodriguez.nodocivico.R
import com.rodriguez.nodocivico.model.User

class UsuariosAdapter(
    private val lista: List<User>,
    private val onEditar: (User) -> Unit,
    private val onEliminar: (User) -> Unit,
    private val onToggleActivo: (User) -> Unit
) : RecyclerView.Adapter<UsuariosAdapter.UsuarioViewHolder>() {

    inner class UsuarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre:   TextView     = view.findViewById(R.id.tvNombreUsuario)
        val tvCorreo:   TextView     = view.findViewById(R.id.tvCorreoUsuario)
        val tvRol:      TextView     = view.findViewById(R.id.tvRolUsuario)
        val switchActivo: SwitchCompat = view.findViewById(R.id.switchActivo)
        val btnEditar:  MaterialButton = view.findViewById(R.id.btnEditarUsuario)
        val btnEliminar: MaterialButton = view.findViewById(R.id.btnEliminarUsuario)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = lista[position]
        holder.tvNombre.text  = usuario.nombre
        holder.tvCorreo.text  = usuario.correo
        holder.tvRol.text     = usuario.rol.replaceFirstChar { it.uppercase() }

        holder.switchActivo.isChecked = usuario.activo
        holder.switchActivo.setOnCheckedChangeListener(null)
        holder.switchActivo.setOnCheckedChangeListener { _, _ -> onToggleActivo(usuario) }

        holder.btnEditar.setOnClickListener  { onEditar(usuario) }
        holder.btnEliminar.setOnClickListener { onEliminar(usuario) }
    }

    override fun getItemCount() = lista.size
}