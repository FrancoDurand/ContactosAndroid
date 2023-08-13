package com.franco.contactos

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
	private val retrofit: Retrofit = Retrofit.Builder()
		.baseUrl("https://contactosapi.somee.com/api/")
		.addConverterFactory(GsonConverterFactory.create())
		.build()

	private val userApi: UserApiClient = retrofit.create(UserApiClient::class.java)
	private val contactApi: ContacApiClient = retrofit.create(ContacApiClient::class.java)

	//USER API

	suspend fun getUsers(): List<User> {
		val response = userApi.getUsers()

		if (response.isSuccessful)
			return response.body()!!

		return emptyList()
	}

	/*suspend fun getUsers(): Result<List<User>> {
		val response: Response<List<User>> = userApi.getUsers()

		return if (response.isSuccessful) {
			Result.success(response.body()!!)
		} else {
			Result.failure(Exception("Failed to get users: ${response.code()} ${response.message()}"))
		}
	}*/

	suspend fun login(user: User): JsonObject? {
		val response: Response<JsonObject> = userApi.login(user)

		if (response.isSuccessful)
			return response.body()

		return JsonObject()
	}

	suspend fun updateName(user: User) {
		userApi.updateName(user)
	}

	suspend fun updateEmail(user: User) {
		userApi.updateEmail(user)
	}

	suspend fun updatePassword(user: User) {
		userApi.updatePassword(user)
	}

	suspend fun register(user: User) {
		userApi.register(user)
	}

	//CONTACT API

	suspend fun getContacts(user: User): List<Contact> {
		val response = contactApi.getContacts(user)

		if (response.isSuccessful)
			return response.body()!!

		return emptyList()
	}

	suspend fun addContact(user: User, contactId: Int) {
		val requestData = mapOf(
			"userId" to user.id,
			"contactId" to contactId
		)

		contactApi.addContact(requestData)
	}

	suspend fun deleteContact(user: User, contactId: Int) {
		val requestData = mapOf(
			"userId" to user.id,
			"contactId" to contactId
		)

		contactApi.deleteContact(requestData)
	}

	suspend fun getContactsNoAdded(user: User): List<Contact> {
		val requestData = mapOf(
			"userId" to user.id
		)

		val response = contactApi.getContactsNoAdded(requestData)

		if (response.isSuccessful)
			return response.body()!!

		return emptyList()
	}
}