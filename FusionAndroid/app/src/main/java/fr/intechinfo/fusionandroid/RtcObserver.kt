package fr.intechinfo.fusionandroid

import org.webrtc.DataChannel
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection


/*class RtcObserver(private val ctx: Rtc) : PeerConnection.Observer  {
    override fun onIceGatheringChange(p0: PeerConnection.IceGatheringState?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAddStream(p0: MediaStream?) {
        ctx.mediaStream = p0
    }

     override fun onIceCandidate(p0: IceCandidate?) {
        HttpExecute.BuildAPI().SetNewCandidate(p0!!).execute()
    }

    override fun onDataChannel(p0: DataChannel?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSignalingChange(p0: PeerConnection.SignalingState?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRemoveStream(p0: MediaStream?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onIceConnectionChange(p0: PeerConnection.IceConnectionState?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRenegotiationNeeded() {
        ctx.negotiationNeeded()
    }
}*/