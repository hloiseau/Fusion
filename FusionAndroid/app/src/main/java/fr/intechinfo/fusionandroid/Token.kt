package fr.intechinfo.fusionandroid

class Token {
    internal var token: String? = null

    fun GetToken(): String? {
        return token
    }

    fun SetToken(newToken: String) {
        token = newToken
    }
}
