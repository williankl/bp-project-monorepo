package williankl.bpProject.common.features.authentication

import kotlinx.coroutines.CoroutineDispatcher
import williankl.bpProject.common.core.models.network.response.UserCredentialResponse
import williankl.bpProject.common.data.auth.AuthService
import williankl.bpProject.common.data.auth.model.LoginData
import williankl.bpProject.common.data.sessionHandler.PreferencesHandler
import williankl.bpProject.common.platform.stateHandler.RunnerModel

internal class AuthenticationRunnerModel(
    private val authService: AuthService,
    private val preferencesHandler: PreferencesHandler,
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
        ).handleSuccessfulLogin()
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
        ).handleSuccessfulLogin()
    }

    private fun UserCredentialResponse.handleSuccessfulLogin() {
        preferencesHandler.setBearerToken(
            token = bearerToken,
        )
    }
}
