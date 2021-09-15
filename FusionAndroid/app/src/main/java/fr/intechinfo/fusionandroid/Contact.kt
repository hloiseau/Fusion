package fr.intechinfo.fusionandroid

class Contact {
    private var Name: String? = null
    private var Number: String? = null

    fun GetName(): String? {
        return Name
    }

    fun GetNumber(): String? {
        return Number
    }

    fun SetName(name: String) {
        Name = name
    }

    fun SetNumber(number: String) {
        Number = number
    }

}
