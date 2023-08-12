package com.franco.contactos

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ContacApiClient {
	@POST("contact")
	suspend fun getContacts(@Body user: User): Response<List<Contact>>

	@POST("contact/add")
	suspend fun addContact(@Body requestData: Map<String, Int?>)

	@POST("contact/delete")
	suspend fun deleteContact(@Body requestData: Map<String, Int?>)

	@POST("contact/noAdded")
	suspend fun getContactsNoAdded(@Body requestData: Map<String, Int?>): Response<List<Contact>>
}
