package williankl.bpProject.common.data.auth

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import williankl.bpProject.common.core.models.network.response.UserCredentialResponse
import williankl.bpProject.common.data.auth.model.LoginData
import williankl.bpProject.common.data.cypher.BeautifulCypher

internal class AuthInfrastructure(
    private val client: HttpClient,
    private val cypher: BeautifulCypher,
) : AuthService {

    private companion object {
        private const val LOGIN_ENDPOINT = "/auth/login"
        private const val SIGNUP_ENDPOINT = "/auth/signup"
    }

    override suspend fun logIn(loginData: LoginData): UserCredentialResponse {
        return client.post(LOGIN_ENDPOINT) {
            parameter("credential", loginData.userName ?: loginData.email)
            parameter("password", loginData.password)
        }.body()
    }

    override suspend fun signUp(loginData: LoginData): UserCredentialResponse {
        return client.post(SIGNUP_ENDPOINT) {
            parameter("email", loginData.email)
            parameter("tag", loginData.userName)
            parameter("password", cypher.encrypt(loginData.password))
        }.body()
    }
}
