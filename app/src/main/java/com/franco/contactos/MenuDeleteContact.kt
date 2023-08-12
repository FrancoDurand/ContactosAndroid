package com.franco.contactos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuDeleteContact : AppCompatActivity() {
	private lateinit var user: User
	private val apiClient = ApiClient()
	private lateinit var pbCarga: ProgressBar
	private lateinit var txtUser: TextView
	private lateinit var contacts: List<Contact>
	private lateinit var txtId: EditText
	private lateinit var btnAdd: Button

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_menu_delete_contact)

		setId()
		setListener()
		getContactsAdded()
	}

	private fun setId() {
		user = intent.getSerializableExtra("user") as User
		txtUser = findViewById(R.id.txtContacts3)
		pbCarga = findViewById(R.id.pbCarga4)
		txtId = findViewById(R.id.txtId2)
		btnAdd = findViewById(R.id.btnDelete)
		pbCarga.visibility = View.VISIBLE
	}

	@SuppressLint("SetTextI18n")
	private fun getContactsAdded() {
		CoroutineScope(Dispatchers.IO).launch {
			contacts = apiClient.getContacts(user)

			withContext(Dispatchers.Main) {
				pbCarga.visibility = View.GONE
				txtUser.text = "Contactos agregados:\n\n\n"

				for (user in contacts) {
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

	private fun setListener() {
		btnAdd.setOnClickListener { deleteContact() }
	}

	private fun isValid(id: Int): Boolean {
		for (user in contacts)
			if (user.id == id)
				return true

		return false
	}

	private fun deleteContact() {
		if (txtId.text.isNotEmpty()) {
			val id = txtId.text.toString().toInt()

			if (isValid(id))
				CoroutineScope(Dispatchers.IO).launch {
					apiClient.deleteContact(user, id)
					withContext(Dispatchers.Main) {
						txtUser.text = ""
						showToast("Contacto eliminado")
						pbCarga.visibility = View.VISIBLE
					}
					getContactsAdded()
				}
			else {
				showToast("ID no valido")
			}
		} else {
			showToast("Rellene el campo")
		}
	}

	private fun showToast(message: String) {
		Toast.makeText(this@MenuDeleteContact, message, Toast.LENGTH_SHORT).show()
	}
}