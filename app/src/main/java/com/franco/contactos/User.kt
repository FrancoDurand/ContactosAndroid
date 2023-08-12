package com.franco.contactos

import java.io.Serializable

data class User(var id: Int?, var name: String, var email: String, var password: String) :
	Serializable
