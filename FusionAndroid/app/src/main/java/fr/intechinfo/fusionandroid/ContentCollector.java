package fr.intechinfo.fusionandroid;

import java.util.List;
import android.provider.ContactsContract;

public class ContentCollector {
    public ContentCollector(){

    }

    public List<Contact> GetContacts(){
        String[] mProjection = {
                ContactsContract.PhoneLookup.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER

        };
        return null; //todo
    }
}
