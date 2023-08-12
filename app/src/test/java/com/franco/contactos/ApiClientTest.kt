package com.franco.contactos

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ApiClientTest {
	private lateinit var apiClient: ApiClient
	@Before
	fun prepare(){
		apiClient = ApiClient()
	}

	@Test
	fun getUsers() = runBlocking {
		try{
			val users = apiClient.getUsers()

			for (user in users)
					println("${user.id} + ${user.name} + ${user.email} + ${user.password}")
		}
		catch (e: Exception){
			println(e.message)
		}
	}

	@Test
	fun login() = runBlocking {
		try{
			val response = apiClient.login(User(0,"","bob","1"))

			if (response != null) {
				val id = response.get("id").asInt
				val name = response.get("name").asString

				println("ID: $id, Name: $name")
			}
		}
		catch (e: Exception){
			println(e.message)
		}

	}

	@Test
	fun updateName()= runBlocking {
		try{
			apiClient.updateName(User(5, "BOBO", "", ""))
		}
		catch (e: Exception){
			println(e.message)
		}

	}

	@Test
	fun updateEmail()= runBlocking {
		try{
			apiClient.updateEmail(User(5, "BOBO", "BOBO", ""))
		}
		catch (e: Exception){
			println(e.message)
		}
	}

	@Test
	fun updatePassword()= runBlocking {
		try{
			apiClient.updatePassword(User(5, "BOBO", "", "1"))
		}
		catch (e: Exception){
			println(e.message)
		}
	}

	@Test
	fun register()= runBlocking {
		try{
			apiClient.register(User(null, "android", "android", "1"))
		}
		catch (e: Exception){
			println(e.message)
		}
	}

	@Test
	fun addContact() = runBlocking {
		try {
			apiClient.addContact(User(1,"","",""), 24)
		}
		catch (e: Exception){
			println(e.message)
		}
	}

	@Test
	fun deleteContact() = runBlocking {
		try {
			apiClient.deleteContact(User(1,"","",""), 24)
		}
		catch (e: Exception){
			println(e.message)
		}
	}

	@Test
	fun getContactsNoAdded() = runBlocking {
		try {
			val contacts = apiClient.getContactsNoAdded(User(1,"","",""))

			for (c in contacts){
				println(c.id)
				println(c.name)
				println(c.email + "\n")
			}
		}
		catch (e: Exception){
			println(e.message)
		}
	}

}