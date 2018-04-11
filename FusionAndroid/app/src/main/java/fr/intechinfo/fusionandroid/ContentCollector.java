package fr.intechinfo.fusionandroid;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class ContentCollector {
    public ContentCollector(){

    }

    public List<Contact> GetContacts(Context context){
        List<Contact> lstContact = new ArrayList<Contact>();
        Contact contact = new Contact();
        Uri message = Uri.parse("content://contacts/people");
        ContentResolver cr = context.getContentResolver();

        Cursor c =cr.query(message, null, null, null, null);
        int totalContacts = c.getCount();

        if(c.moveToFirst()){
            for(int i = 0; i<totalContacts; i++){
                contact = new Contact();
                contact.SetName(c.getString(c.getColumnIndexOrThrow("NAME")));
                contact.SetNumber(c.getString(c.getColumnIndexOrThrow("NUMBER")));
                lstContact.add(contact);
                c.moveToNext();
            }
        } else {
            throw new RuntimeException("you have no Contacts");
        }
        c.close();

        return lstContact;
    }
}
