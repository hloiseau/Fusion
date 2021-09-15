package fr.intechinfo.fusionandroid

class ContactsList {
    internal var Contacts: List<Contact>? = null

    fun GetContact(): List<Contact>? {
        return this.Contacts
    }

    fun SetContact(contact: List<Contact>) {
        this.Contacts = contact
    }
}
