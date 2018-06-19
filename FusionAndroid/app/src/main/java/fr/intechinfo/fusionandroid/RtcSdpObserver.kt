package fr.intechinfo.fusionandroid

import android.util.Log
import org.webrtc.SdpObserver
import org.webrtc.SessionDescription

class RtcSdpObserver : SdpObserver {
    override fun onSetFailure(p0: String?) {
        Log.d(RtcObserver.TAG, p0)
    }

    override fun onSetSuccess() {
        if(Rtc.instance.peerConnection!!.remoteDescription!!.type == SessionDescription.Type.OFFER){
            Rtc.instance.peerConnection!!.createAnswer(this, Rtc.instance.audioConstraints)
        }
    }

    override fun onCreateSuccess(p0: SessionDescription?) {
        Rtc.instance.peerConnection!!.setLocalDescription(this, p0)
        HttpExecute(HttpExecute.BuildAPI().SetLocalDesc(RtcInfo(null, Rtc.instance.peerConnection!!.localDescription.description))).start()
    }


    override fun onCreateFailure(p0: String?) {
        Log.d(RtcObserver.TAG, p0)
    }
}