package com.example.myapplication.data

import com.example.myapplication.data.datastore.DeviceTokenStore
import com.example.myapplication.network.DeviceApi
import com.example.myapplication.network.dto.ActivateRequestDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class DeviceRepository(
    private val api: DeviceApi,
    private val tokenStore: DeviceTokenStore
) {

    suspend fun activate(code: String, deviceName: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.activate(
                    ActivateRequestDto(
                        code = code,
                        deviceName = deviceName
                    )
                )

                tokenStore.saveToken(response.token)

                Result.success(Unit)

            } catch (e: HttpException) {
                Result.failure(e)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}