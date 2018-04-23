package fr.intechinfo.fusionandroid;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;

public class ContentCollector {
    public ContentCollector(){

    }

    public List<Contact> GetContacts(Context context){
        List<Contact> lstContact = new ArrayList<Contact>();
        Contact contact;
        Uri message = Uri.parse("content://contacts/people");
        ContentResolver cr = context.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
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

    public List<SMS> GetSMS(Context context){
        List<SMS> lstSMS = new ArrayList<>();
        SMS sms;
        Uri message = Telephony.MmsSms.CONTENT_URI;
        ContentResolver cr = context.getContentResolver();

        Cursor c =cr.query(message, null, null, null, null);
        int totalSMS = c.getCount();

        if(c.moveToFirst()){
            for(int i = 0; i<totalSMS; i++) {
                String address = c.getString(c.getColumnIndexOrThrow("ADDRESS"));
                String body = c.getString(c.getColumnIndexOrThrow("BODY"));
                String creator = c.getString(c.getColumnIndexOrThrow("CREATOR"));
                String date = c.getString(c.getColumnIndexOrThrow("DATE"));
                String type = c.getString(c.getColumnIndexOrThrow("TYPE"));
                sms = new SMS(address, body, creator, date, type);
                lstSMS.add(sms);
                c.moveToNext();
            }
        } else {
            throw new RuntimeException("you have no SMS");
        }
        c.close();

        return lstSMS;
    }
}
