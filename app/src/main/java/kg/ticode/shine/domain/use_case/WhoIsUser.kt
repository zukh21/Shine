package kg.ticode.shine.domain.use_case

sealed class WhoIsUser{
    object IsAdmin: WhoIsUser()
    object IsManager: WhoIsUser()
    object IsJustUser: WhoIsUser()
}
