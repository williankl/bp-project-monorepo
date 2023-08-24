package williankl.bpProject.common.data.auth

import williankl.bpProject.common.data.auth.model.LoginData

public interface AuthService {
    public suspend fun logIn(loginData: LoginData)

    public suspend fun signUp(loginData: LoginData)
}
