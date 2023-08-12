package com.franco.contactos

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApiClient {
	@GET("user")
	suspend fun getUsers(): Response<List<User>>

	@POST("user/login")
	suspend fun login(@Body user: User): Response<JsonObject>

	@PUT("user/updateName")
	suspend fun updateName(@Body user: User)

	@PUT("user/updateEmail")
	suspend fun updateEmail(@Body user: User)

	@PUT("user/updatePass")
	suspend fun updatePassword(@Body user: User)

	@POST("user/register")
	suspend fun register(@Body user: User)
}