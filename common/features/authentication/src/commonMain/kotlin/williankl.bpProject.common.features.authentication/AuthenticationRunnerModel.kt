package williankl.bpProject.common.features.authentication

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.data.auth.AuthService
import williankl.bpProject.common.data.auth.model.LoginData
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class AuthenticationRunnerModel(
    private val authService: AuthService,
    dispatcher: CoroutineDispatcher,
) : RunnerModel<Unit>(
    initialData = Unit,
    dispatcher = dispatcher
) {

    fun logIn(
        login: String,
        password: String
    ) = runAsync {
        authService.logIn(
            loginData = LoginData(
                email = login,
                password = password
            )
        )
    }

    fun signUp(
        userName: String,
        login: String,
        password: String
    ) = runAsync {
        authService.signUp(
            loginData = LoginData(
                userName = userName,
                email = login,
                password = password
            )
        )
    }
}