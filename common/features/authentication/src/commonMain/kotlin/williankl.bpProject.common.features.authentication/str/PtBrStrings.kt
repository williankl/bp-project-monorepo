package williankl.bpProject.common.features.authentication.str

import cafe.adriel.lyricist.LyricistStrings
import williankl.bpProject.common.features.authentication.str.AuthenticationStrings.LoginRequiredStrings

@LyricistStrings(languageTag = "pt-BR", default = true)
internal val ptBrStrings = AuthenticationStrings(
    userNameHint = "Digite seu nome completo",
    loginHint = "Digite seu e-mail...",
    passwordHint = "Senha",
    signupActionLabel = "Cadastrar",
    loginActionLabel = "Entrar",
    forgotPasswordLabel = "Esqueci minha senha",
    signUpLabel = "Cadastre-se",
    logInLabel = "Faça o Login",
    hasNoAccountLabel = "Ainda não possui conta?",
    alreadyHasAccountLabel = "Já possui conta?",
    loginRequiredStrings = LoginRequiredStrings(
        title = "Entrar no Beautiful Places",
        subtitle = "Gerencie sua conta, verifique notificações, monte seus roteiros e muito mais.",
        defaultOptionLabel = "Entrar na sua conta ",
        facebookOptionLabel = "Entrar com o facebook",
        googleOptionLabel = "Entrar com o e-mail",
    )
)
