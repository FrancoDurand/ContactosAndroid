package com.franco.contactos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
	private lateinit var btnVerUsuarios: Button
	private lateinit var btnRegistro: Button
	private lateinit var btnLogin: Button


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		setId()
		setListener()
	}

	private fun setId() {
		btnVerUsuarios = findViewById(R.id.btnVerUsuarios)
		btnRegistro = findViewById(R.id.btnRegistro)
		btnLogin = findViewById(R.id.btnLogin)
	}

	private fun setListener() {
		btnVerUsuarios.setOnClickListener {
			val intent = Intent(this, UsuariosRegistrados::class.java)
			startActivity(intent)
		}

		btnRegistro.setOnClickListener {
			val intent = Intent(this, Registro::class.java)
			startActivity(intent)
		}

		btnLogin.setOnClickListener {
			val intent = Intent(this, MenuLogin::class.java)
			startActivity(intent)
		}
	}
}