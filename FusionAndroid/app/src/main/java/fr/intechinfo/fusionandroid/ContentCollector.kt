package fr.intechinfo.fusionandroid

import java.util.ArrayList

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Telephony

class ContentCollector {


    fun GetContacts(context: Context): List<Contact> {
        val lstContact = ArrayList<Contact>()
        var contact: Contact


        val c = context.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)

        while (c!!.moveToNext()) {
            contact = Contact()
            contact.SetName(c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)))
            val hasPhone = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))
            val id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
            if (hasPhone == "1") {
                val phones = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null)
                if (phones!!.moveToFirst()) {
                    contact.SetNumber(phones.getString(phones.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)))
                }
                phones.close()
            }
            lstContact.add(contact)
        }
        c.close()

        return lstContact
    }

    fun GetSMS(context: Context): List<SMS> {
        val lstSMS = ArrayList<SMS>()
        var sms: SMS
        val message = Telephony.Sms.CONTENT_URI
        val cr = context.contentResolver

        val c = cr.query(message, null, null, null, null)
        val totalSMS = c!!.count

        if (c.moveToFirst()) {
            var i = 0
            while (i < totalSMS) {
                val address = c.getString(c.getColumnIndexOrThrow("ADDRESS"))
                val body = c.getString(c.getColumnIndexOrThrow("BODY"))
                val date = c.getString(c.getColumnIndexOrThrow("DATE"))
                val type = c.getString(c.getColumnIndexOrThrow("TYPE"))
                sms = SMS(address, body, date, type)
                if (type == "1" || type == "2") {
                    lstSMS.add(sms)
                }
                c.moveToNext()
                i++
            }
        } else {
            throw RuntimeException("you have no SMS")
        }
        c.close()

        return lstSMS
    }
}
