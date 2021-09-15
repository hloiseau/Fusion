package fr.intechinfo.fusionandroid

class SMSList {
    internal var sms: List<SMS>? = null

    fun GetSMS(): List<SMS>? {
        return this.sms
    }

    fun SetSMS(sms: List<SMS>) {
        this.sms = sms
    }
}
