package com.franco.contactos

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuariosRegistrados : AppCompatActivity() {
	private lateinit var txtUser: TextView
	private val apiClient = ApiClient()
	private lateinit var pbCarga: ProgressBar

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_usuarios_registrados)

		setId()

		getUsers()
	}

	private fun getUsers() {
		CoroutineScope(Dispatchers.IO).launch {
			val result = apiClient.getUsers()

			withContext(Dispatchers.Main) {
				pbCarga.visibility = View.GONE
				txtUser.text = "Usuarios Registrados:\n"

				for (user in result) {
					val txt = "ID: ${user.id}\n" +
							"Nombre: ${user.name}\n" +
							"Email: ${user.email}\n" +
							"Password: ${user.password}\n\n"

					txtUser.text.let { current ->
						txtUser.text = current.toString() + txt
					}
				}
			}
		}
	}

	private fun setId() {
		txtUser = findViewById(R.id.txtUsuarios)
		pbCarga = findViewById(R.id.pbCarga)
		pbCarga.visibility = View.VISIBLE
	}
}