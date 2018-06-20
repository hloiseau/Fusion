package fr.intechinfo.fusionandroid

import android.util.Log
import org.webrtc.DataChannel
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection


class RtcObserver : PeerConnection.Observer  {
    override fun onIceConnectionReceivingChange(p0: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onIceGatheringChange(p0: PeerConnection.IceGatheringState?) {
        Log.d(TAG, p0?.toString())
    }

    override fun onAddStream(p0: MediaStream?) {
        Rtc.instance.mediaStream = p0!!
    }

     override fun onIceCandidate(p0: IceCandidate?) {
         HttpExecute(HttpExecute.BuildAPI().SetNewCandidate(RtcInfo(p0!!, null))).start()
    }

    override fun onDataChannel(p0: DataChannel?) {
        Log.d(TAG, p0?.toString())
    }

    override fun onSignalingChange(p0: PeerConnection.SignalingState?) {
        Log.d(TAG, p0?.toString())
    }

    override fun onRemoveStream(p0: MediaStream?) {
        Log.d(TAG, p0?.toString())
    }

    override fun onIceConnectionChange(p0: PeerConnection.IceConnectionState?) {
        Log.d(TAG, p0?.toString())
    }

    override fun onRenegotiationNeeded() {
        Rtc.instance.negotiationNeeded()
    }
    companion object {
        val TAG = "RtcObserver"
    }
}