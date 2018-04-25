package fr.intechinfo.fusionandroid;

import java.util.List;

public class ContactsList {
    List<Contact> Contacts;

    public List<Contact> GetContact(){
        return this.Contacts;
    }

    public void SetContact(List<Contact> contact) {
        this.Contacts = contact;
    }
}
