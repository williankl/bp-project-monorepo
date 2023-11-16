package williankl.bpProject.common.data.auth

import williankl.bpProject.common.data.auth.model.LoginData
import williankl.bpProject.common.data.networking.core.models.response.UserCredentialResponse

public interface AuthService {
    public suspend fun logIn(loginData: LoginData): UserCredentialResponse
    public suspend fun logOut()
    public suspend fun signUp(loginData: LoginData): UserCredentialResponse
}
