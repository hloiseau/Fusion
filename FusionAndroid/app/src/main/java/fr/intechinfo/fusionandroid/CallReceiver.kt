package fr.intechinfo.fusionandroid

import android.content.Context
import java.util.*

class CallReceiver : IncomingCallBroadcastReceiver() {
    override fun onIncomingCallStarted(ctx: Context, number: String, start: Date) {
        HttpExecute(HttpExecute.BuildAPI().NewCall(number)).start()
    }
    override fun onOutgoingCallStarted(ctx: Context, number: String, start: Date) {

    }
    override fun onIncomingCallEnded(ctx: Context, number: String, start: Date, end: Date) {

    }
    override fun onOutgoingCallEnded(ctx: Context, number: String, start: Date, end: Date) {

    }
    override fun onMissedCall(ctx: Context, number: String, start: Date) {

    }
}