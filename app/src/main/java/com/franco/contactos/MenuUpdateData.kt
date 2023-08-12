package com.franco.contactos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuUpdateData : AppCompatActivity() {
	private lateinit var user: User
	private lateinit var btnShowOptions: Button
	private lateinit var btnAccept: Button
	private lateinit var txtUpdate: EditText
	private val apiClient = ApiClient()
	private var optionValue: Int = -1 // Declare the optionValue variable

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_menu_update_data)

		setId()
		setListener()
	}

	private fun setId() {
		user = intent.getSerializableExtra("user") as User
		btnShowOptions = findViewById(R.id.btnShowOptions)
		txtUpdate = findViewById(R.id.editText)
		btnAccept = findViewById(R.id.btnAccept)
	}

	private fun setListener() {
		btnShowOptions.setOnClickListener { showPopupMenu() }
		btnAccept.setOnClickListener { updateData() }
	}

	private fun updateData() {
		val editTextValue = txtUpdate.text.toString().trim()

		if (editTextValue.isNotEmpty()) {
			when (optionValue) {
				1 -> {
					// Cambiar Nombre de Usuario
					user.name = editTextValue
					CoroutineScope(Dispatchers.IO).launch {
						try {
							apiClient.updateName(user)
						} catch (_: Exception) {
						}

						withContext(Dispatchers.Main) {
							showToast("Nombre de usuario cambiado")
						}
					}
				}

				2 -> {
					// Cambiar Correo Electrónico
					user.email = editTextValue
					CoroutineScope(Dispatchers.IO).launch {
						try {
							apiClient.updateEmail(user)
						} catch (_: Exception) {
						}

						withContext(Dispatchers.Main) {
							showToast("Correo electrónico cambiado")
						}
					}
				}

				3 -> {
					// Cambiar Contraseña
					user.password = editTextValue
					CoroutineScope(Dispatchers.IO).launch {
						try {
							apiClient.updatePassword(user)
						} catch (_: Exception) {
						}

						withContext(Dispatchers.Main) {
							showToast("Contraseña cambiada")
						}
					}
				}

				else -> showToast("Opción inválida")
			}
		} else {
			showToast("Rellene el campo")
		}
	}

	@SuppressLint("ResourceType")
	private fun showPopupMenu() {
		val popupMenu = PopupMenu(this, btnShowOptions)
		popupMenu.menuInflater.inflate(R.xml.change_options_menu, popupMenu.menu)

		popupMenu.setOnMenuItemClickListener { item: MenuItem ->
			optionValue = when (item.order) {
				1 -> 1 // Cambiar Nombre de Usuario
				2 -> 2 // Cambiar Correo Electrónico
				3 -> 3 // Cambiar Contraseña
				else -> -1 // Valor por defecto
			}
			true
		}

		popupMenu.show()
	}

	private fun showToast(message: String) {
		Toast.makeText(this@MenuUpdateData, message, Toast.LENGTH_SHORT).show()
	}
}
