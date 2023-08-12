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

class MenuAddContact : AppCompatActivity() {
	private lateinit var user: User
	private val apiClient = ApiClient()
	private lateinit var pbCarga: ProgressBar
	private lateinit var txtUser: TextView
	private lateinit var contacts: List<Contact>
	private lateinit var txtId: EditText
	private lateinit var btnAdd: Button

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_menu_add_contact)

		setId()
		setListener()
		getContactsNoAdded()
	}

	private fun setId() {
		user = intent.getSerializableExtra("user") as User
		txtUser = findViewById(R.id.txtContacts2)
		pbCarga = findViewById(R.id.pbCarga3)
		txtId = findViewById(R.id.txtId)
		btnAdd = findViewById(R.id.btnAdd)
		pbCarga.visibility = View.VISIBLE
	}

	@SuppressLint("SetTextI18n")
	private fun getContactsNoAdded() {
		CoroutineScope(Dispatchers.IO).launch {
			contacts = apiClient.getContactsNoAdded(user)

			withContext(Dispatchers.Main) {
				pbCarga.visibility = View.GONE
				txtUser.text = "Contactos no agregados:\n\n\n"

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
		btnAdd.setOnClickListener { addContact() }
	}

	private fun isValid(id: Int): Boolean {
		for (user in contacts)
			if (user.id == id)
				return true

		return false
	}

	private fun addContact() {
		if (txtId.text.isNotEmpty()) {
			val id = txtId.text.toString().toInt()

			if (isValid(id))
				CoroutineScope(Dispatchers.IO).launch {
					apiClient.addContact(user, id)
					withContext(Dispatchers.Main) {
						txtUser.text = ""
						showToast("Contacto agregado")
						pbCarga.visibility = View.VISIBLE
					}
					getContactsNoAdded()
				}
			else {
				showToast("ID no valido")
			}

		} else {
			showToast("Rellene el campo")
		}
	}

	private fun showToast(message: String) {
		Toast.makeText(this@MenuAddContact, message, Toast.LENGTH_SHORT).show()
	}
}