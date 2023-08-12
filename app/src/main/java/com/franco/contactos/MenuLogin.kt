package com.franco.contactos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MenuLogin : AppCompatActivity() {
	private val apiClient = ApiClient()
	private lateinit var txtEmail: EditText
	private lateinit var txtPassword: EditText
	private lateinit var btnLogin: Button

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_menu_login)

		setId()
		setListener()
	}

	private fun setId() {
		txtEmail = findViewById(R.id.txtEmailLogin)
		txtPassword = findViewById(R.id.txtPasswordLogin)
		btnLogin = findViewById(R.id.btnLoginLogin)
	}

	private fun setListener() {
		btnLogin.setOnClickListener {
			if (txtEmail.text.isNotEmpty() and txtPassword.text.isNotEmpty())
				CoroutineScope(Dispatchers.IO).launch {
					val user = User(null, "", txtEmail.text.toString(), txtPassword.text.toString())
					val response = apiClient.login(user)

					if (response != null) {
						user.id = response.get("id").asInt
						user.name = response.get("name").asString
						withContext(Dispatchers.Main) {
							val intent = Intent(this@MenuLogin, MenuUsuario::class.java).apply {
								putExtra("user", user)
							}
							startActivity(intent)
						}
					} else {
						withContext(Dispatchers.Main) {
							Toast.makeText(this@MenuLogin, "Datos no validos", Toast.LENGTH_SHORT)
								.show()
						}
					}
				}
			else
				Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT)
					.show()
		}
	}
}
