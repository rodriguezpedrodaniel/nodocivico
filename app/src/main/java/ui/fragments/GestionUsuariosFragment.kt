package com.rodriguez.nodocivico.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rodriguez.nodocivico.R
import com.rodriguez.nodocivico.model.User
import com.rodriguez.nodocivico.network.RetrofitClient
import kotlinx.coroutines.launch
import com.rodriguez.nodocivico.adapter.UsuariosAdapter


class GestionUsuariosFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private val listaUsuarios = mutableListOf<User>()
    private lateinit var adapter: UsuariosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_gestion_usuarios, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvUsuarios)
        fab = view.findViewById(R.id.fabAgregarUsuario)

        adapter = UsuariosAdapter(
            listaUsuarios,
            onEditar = { usuario -> mostrarDialogoUsuario(usuario) },
            onEliminar = { usuario -> confirmarEliminar(usuario) },
            onToggleActivo = { usuario -> toggleActivo(usuario) }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        fab.setOnClickListener { mostrarDialogoUsuario(null) }

        cargarUsuarios()
    }

    private fun cargarUsuarios() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.obtenerUsuarios()
                if (response.isSuccessful) {
                    listaUsuarios.clear()
                    listaUsuarios.addAll(response.body() ?: emptyList())
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "❌ Error cargando usuarios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarDialogoUsuario(usuarioExistente: User?) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_usuario, null)

        val etNombre   = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etNombreDialog)
        val etCorreo   = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etCorreoDialog)
        val etPassword = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etPasswordDialog)
        val etTelefono = dialogView.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etTelefonoDialog)
        val spinnerRol = dialogView.findViewById<android.widget.Spinner>(R.id.spinnerRol)

        // Precargar datos si es edición
        usuarioExistente?.let {
            etNombre.setText(it.nombre)
            etCorreo.setText(it.correo)
            etPassword.setText(it.password)
            etTelefono.setText(it.telefono)
            val roles = resources.getStringArray(R.array.roles_array)
            spinnerRol.setSelection(roles.indexOf(it.rol).takeIf { i -> i >= 0 } ?: 0)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(if (usuarioExistente == null) "Crear Usuario" else "Editar Usuario")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val nombre   = etNombre.text.toString().trim()
                val correo   = etCorreo.text.toString().trim()
                val password = etPassword.text.toString().trim()
                val telefono = etTelefono.text.toString().trim()
                val rol      = spinnerRol.selectedItem.toString()

                if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Completa los campos obligatorios", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val user = User(
                    id       = usuarioExistente?.id ?: "",
                    nombre   = nombre,
                    correo   = correo,
                    password = password,
                    telefono = telefono,
                    rol      = rol,
                    activo   = usuarioExistente?.activo ?: true
                )

                if (usuarioExistente == null) crearUsuario(user)
                else editarUsuario(user)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun crearUsuario(user: User) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.crearUsuario(user)
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "✅ Usuario creado", Toast.LENGTH_SHORT).show()
                    cargarUsuarios()
                } else {
                    Toast.makeText(requireContext(), "❌ Error al crear usuario", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "❌ Sin conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun editarUsuario(user: User) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.actualizarUsuario(user.id, user)
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "✅ Usuario actualizado", Toast.LENGTH_SHORT).show()
                    cargarUsuarios()
                } else {
                    Toast.makeText(requireContext(), "❌ Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "❌ Sin conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun confirmarEliminar(usuario: User) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Eliminar usuario")
            .setMessage("¿Seguro que deseas eliminar a ${usuario.nombre}?")
            .setPositiveButton("Eliminar") { _, _ ->
                lifecycleScope.launch {
                    try {
                        val response = RetrofitClient.api.eliminarUsuario(usuario.id)
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "✅ Usuario eliminado", Toast.LENGTH_SHORT).show()
                            cargarUsuarios()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "❌ Sin conexión", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun toggleActivo(usuario: User) {
        lifecycleScope.launch {
            try {
                val actualizado = usuario.copy(activo = !usuario.activo)
                val response = RetrofitClient.api.actualizarUsuario(usuario.id, actualizado)
                if (response.isSuccessful) {
                    val msg = if (actualizado.activo) "✅ Usuario activado" else "⚠️ Usuario desactivado"
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
                    cargarUsuarios()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "❌ Sin conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }
}