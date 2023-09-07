package williankl.bpProject.common.data.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import williankl.bpProject.common.core.models.network.response.UserCredentialResponse
import williankl.bpProject.common.data.auth.model.LoginData
import williankl.bpProject.common.data.auth.model.SecretData

internal class AuthInfrastructure(
    private val client: HttpClient,
    private val json: Json,
) : AuthService {

    private companion object {
        private const val LOGIN_ENDPOINT = "/auth/login"
        private const val SIGNUP_ENDPOINT = "/auth/signup"
    }

    override suspend fun logIn(loginData: LoginData): UserCredentialResponse {
        return client.get(LOGIN_ENDPOINT) {
            setBody(
                SecretData(
                    secret = encryptData(loginData)
                )
            )
        }.body()
    }

    override suspend fun signUp(loginData: LoginData): UserCredentialResponse {
        return client.post(SIGNUP_ENDPOINT) {
            setBody(
                SecretData(
                    secret = encryptData(loginData)
                )
            )
        }.body()
    }

    private inline fun <reified T> encryptData(base: T): String {
        return json.encodeToString(base)
    }
}
