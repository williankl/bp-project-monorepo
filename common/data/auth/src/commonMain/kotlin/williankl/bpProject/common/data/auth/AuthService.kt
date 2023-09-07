package williankl.bpProject.common.data.auth

import williankl.bpProject.common.core.models.network.response.UserCredentialResponse
import williankl.bpProject.common.data.auth.model.LoginData

public interface AuthService {
    public suspend fun logIn(loginData: LoginData): UserCredentialResponse

    public suspend fun signUp(loginData: LoginData): UserCredentialResponse
}
