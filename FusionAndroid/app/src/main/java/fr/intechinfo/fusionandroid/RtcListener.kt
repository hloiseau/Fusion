package fr.intechinfo.fusionandroid

import android.util.Log
import me.kevingleason.pnwebrtc.PnPeer
import me.kevingleason.pnwebrtc.PnRTCListener
import org.webrtc.AudioTrack
import org.webrtc.MediaStream

class RtcListener : PnRTCListener() {
    override fun onMessage(peer: PnPeer?, message: Any?) {
        super.onMessage(peer, message)
    }
}