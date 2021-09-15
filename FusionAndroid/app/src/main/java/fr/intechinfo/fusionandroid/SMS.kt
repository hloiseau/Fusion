package fr.intechinfo.fusionandroid


class SMS(private val Address: String, private val Body: String, private val Date: String, private val Type: String) {

    fun GetAddress(): String {
        return Address
    }

    fun GetBody(): String {
        return Body
    }

    fun GetDate(): String {
        return Date
    }

    fun GetType(): String {
        return Type
    }


}
