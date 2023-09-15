package williankl.bpProject.common.features.authentication.str

internal data class AuthenticationStrings(
    val userNameHint: String,
    val loginHint: String,
    val passwordHint: String,
    val signupActionLabel: String,
    val loginActionLabel: String,
    val forgotPasswordLabel: String,
    val signUpLabel: String,
    val logInLabel: String,
    val hasNoAccountLabel: String,
    val alreadyHasAccountLabel: String,
    val loginRequiredStrings: LoginRequiredStrings,
) {
    internal data class LoginRequiredStrings(
        val title: String,
        val subtitle: String,
        val defaultOptionLabel: String,
        val facebookOptionLabel: String,
        val googleOptionLabel: String,
    )
}
