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
        List<Contact> lstContact = new ArrayList<>();
        Contact contact;


        Cursor c = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null, null, null);

        while (c.moveToNext()) {
            contact = new Contact();
            contact.SetName(c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)));
            String hasPhone = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
            if (hasPhone.equals("1")){
                Cursor phones = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,null,null);
                if(phones.moveToFirst()){
                    contact.SetNumber(phones.getString(phones.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                }
                phones.close();
            }
            lstContact.add(contact);
        }
        c.close();

        return lstContact;
    }

    public List<SMS> GetSMS(Context context){
        List<SMS> lstSMS = new ArrayList<>();
        SMS sms;
        Uri message = Telephony.Sms.CONTENT_URI;
        ContentResolver cr = context.getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        int totalSMS = c.getCount();

        if(c.moveToFirst()){
            for(int i = 0; i<totalSMS; i++) {
                String address = c.getString(c.getColumnIndexOrThrow("ADDRESS"));
                String body = c.getString(c.getColumnIndexOrThrow("BODY"));
                String date = c.getString(c.getColumnIndexOrThrow("DATE"));
                String type = c.getString(c.getColumnIndexOrThrow("TYPE"));
                sms = new SMS(address, body, date, type);
                if(type.equals("1")|| type.equals("2")){
                    lstSMS.add(sms);
                }
                c.moveToNext();
            }
        } else {
            throw new RuntimeException("you have no SMS");
        }
        c.close();

        return lstSMS;
    }
}
