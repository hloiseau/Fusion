package fr.intechinfo.fusionandroid;

import java.util.List;

public class ContactsList {
    List<Contact> contact;

    public List<Contact> GetContact(){
        return this.contact;
    }

    public void SetContact(List<Contact> contact) {
        this.contact = contact;
    }
}
