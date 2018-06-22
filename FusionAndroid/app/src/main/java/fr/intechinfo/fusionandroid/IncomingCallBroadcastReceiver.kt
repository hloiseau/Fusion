package fr.intechinfo.fusionandroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsMessage
import android.telephony.TelephonyManager
import android.util.Log
import java.util.*
import android.media.AudioManager



/**
 * A broadcast receiver who listens for incoming SMS
 */

open class IncomingCallBroadcastReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.NEW_OUTGOING_CALL") {
            savedNumber = intent.extras.getString("android.intent.extra.PHONE_NUMBER")
        }
        else{
            val stateStr = intent.extras.getString(TelephonyManager.EXTRA_STATE)
            val number = intent.extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
            var state = 0
            if(stateStr == TelephonyManager.EXTRA_STATE_IDLE){
                state = TelephonyManager.CALL_STATE_IDLE
            }
            else if(stateStr == TelephonyManager.EXTRA_STATE_OFFHOOK){
                state = TelephonyManager.CALL_STATE_OFFHOOK
            }
            else if(stateStr == TelephonyManager.EXTRA_STATE_RINGING){
                state = TelephonyManager.CALL_STATE_RINGING
            }

            onCallStateChanged(context, state, number);
        }
    }

    protected open fun onIncomingCallStarted(ctx: Context, number: String, start: Date) {}
    protected open fun onOutgoingCallStarted(ctx: Context, number: String, start: Date) {}
    protected open  fun onIncomingCallEnded(ctx: Context, number: String, start: Date, end: Date) {}
    protected open fun onOutgoingCallEnded(ctx: Context, number: String, start: Date, end: Date) {}
    protected open fun onMissedCall(ctx: Context, number: String, start: Date) {}

    fun onCallStateChanged(context: Context, state: Int, number: String) {
        if (lastState == state) {
            //No change, debounce extras
            return
        }
        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                isIncoming = true
                savedNumber = number
                onIncomingCallStarted(context, number, callStartTime!!)
            }
            TelephonyManager.CALL_STATE_OFFHOOK ->
                //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false
                    onOutgoingCallStarted(context, savedNumber!!, callStartTime!!)
                }else{
                    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                    audioManager.isSpeakerphoneOn = true
                }
            TelephonyManager.CALL_STATE_IDLE ->
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                    //Ring but no pickup-  a miss
                    onMissedCall(context, savedNumber!!, callStartTime!!)
                } else if (isIncoming) {
                    onIncomingCallEnded(context, savedNumber!!, callStartTime!!, Date())
                } else {
                    onOutgoingCallEnded(context, savedNumber!!, callStartTime!!, Date())
                }
        }
        lastState = state
    }



    companion object {
        private var lastState = TelephonyManager.CALL_STATE_IDLE
        private val callStartTime: Date? =  Date()
        private var isIncoming: Boolean = false
        private var savedNumber: String? = null  //because the passed incoming is only valid in ringing
        private val TAG = "SmsBroadcastReceiver"
    }
}
