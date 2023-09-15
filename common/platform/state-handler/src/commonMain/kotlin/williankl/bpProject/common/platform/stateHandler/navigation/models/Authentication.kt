package williankl.bpProject.common.platform.stateHandler.navigation.models

public sealed class Authentication : NavigationDestination() {
    public data object LoginRequiredBottomSheet : Authentication()
    public data class Login(
        val startingFlow: AuthenticationFlow = AuthenticationFlow.Login
    ) : Authentication() {
        public enum class AuthenticationFlow {
            Signup,
            Login
        }
    }
}