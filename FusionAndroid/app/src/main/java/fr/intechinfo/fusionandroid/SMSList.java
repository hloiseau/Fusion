package fr.intechinfo.fusionandroid;

import java.util.List;

public class SMSList {
    List<SMS> sms;

    public List<SMS> GetSMS(){
        return this.sms;
    }

    public void SetSMS(List<SMS> sms) {
        this.sms = sms;
    }
}
