package com.franco.contactos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MenuUsuario : AppCompatActivity() {
	private lateinit var user: User
	private lateinit var txtUsername: TextView
	private lateinit var btnUpdateData: Button
	private lateinit var btnViewContacts: Button
	private lateinit var btnAddContact: Button
	private lateinit var btnDeleteContact: Button

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_menu_usuario)

		setId()
		setListener()
	}

	private fun setId() {
		user = intent.getSerializableExtra("user") as User
		txtUsername = findViewById(R.id.txtUsername)
		btnUpdateData = findViewById(R.id.btnUpdateData)
		btnViewContacts = findViewById(R.id.btnViewContacts)
		btnAddContact = findViewById(R.id.btnAddContact)
		btnDeleteContact = findViewById(R.id.btnDeleteContact)

		txtUsername.text = "Bienvenido\n ${user.name}"
	}

	private fun setListener() {
		btnUpdateData.setOnClickListener {
			val intent = Intent(this, MenuUpdateData::class.java)
			intent.putExtra("user", user)
			startActivity(intent)
		}

		btnViewContacts.setOnClickListener {
			val intent = Intent(this, MenuContactos::class.java)
			intent.putExtra("user", user)
			startActivity(intent)
		}

		btnAddContact.setOnClickListener {
			val intent = Intent(this, MenuAddContact::class.java)
			intent.putExtra("user", user)
			startActivity(intent)
		}

		btnDeleteContact.setOnClickListener {
			val intent = Intent(this, MenuDeleteContact::class.java)
			intent.putExtra("user", user)
			startActivity(intent)
		}
	}
}