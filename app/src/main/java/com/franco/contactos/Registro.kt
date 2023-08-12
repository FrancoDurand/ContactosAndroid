package com.franco.contactos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Registro : AppCompatActivity() {
	private val apiClient = ApiClient()
	private lateinit var txtName: EditText
	private lateinit var txtEmail: EditText
	private lateinit var txtPassword: EditText
	private lateinit var btnRegister: Button

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_registro)

		setId()
		setListener()
	}

	private fun setId() {
		txtName = findViewById(R.id.txtNombre)
		txtEmail = findViewById(R.id.txtEmail)
		txtPassword = findViewById(R.id.txtPassword)
		btnRegister = findViewById(R.id.btnRegister)
	}

	private fun setListener() {
		btnRegister.setOnClickListener {
			if (txtName.text.isNotEmpty() and txtEmail.text.isNotEmpty() and txtPassword.text.isNotEmpty())
				CoroutineScope(Dispatchers.IO).launch {
					val user = User(
						null,
						txtName.text.toString(),
						txtEmail.text.toString(),
						txtPassword.text.toString()
					)

					apiClient.register(user)

					withContext(Dispatchers.Main) {
						Toast.makeText(this@Registro, "Registro completado", Toast.LENGTH_SHORT)
							.show()
					}
				}
			else
				Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT)
					.show()
		}
	}
}