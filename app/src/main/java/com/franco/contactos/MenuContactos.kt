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

class MenuContactos : AppCompatActivity() {
	private lateinit var user: User
	private val apiClient = ApiClient()
	private lateinit var pbCarga: ProgressBar
	private lateinit var txtUser: TextView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_menu_contactos)

		setId()
		getContacts()
	}

	private fun setId() {
		user = intent.getSerializableExtra("user") as User
		txtUser = findViewById(R.id.txtContacts)
		pbCarga = findViewById(R.id.pbCarga2)
		pbCarga.visibility = View.VISIBLE
	}

	private fun getContacts() {
		CoroutineScope(Dispatchers.IO).launch {
			val result = apiClient.getContacts(user)

			withContext(Dispatchers.Main) {
				pbCarga.visibility = View.GONE
				txtUser.text = "Contactos:\n\n\n"

				for (user in result) {
					val txt = "ID: ${user.id}\n" +
							"Nombre: ${user.name}\n" +
							"Email: ${user.email}\n\n"
					txtUser.text.let { current ->
						txtUser.text = current.toString() + txt
					}
				}
			}

		}
	}
}