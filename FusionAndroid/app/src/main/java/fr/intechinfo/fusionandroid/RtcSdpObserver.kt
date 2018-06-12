package fr.intechinfo.fusionandroid

import org.webrtc.SdpObserver
import org.webrtc.SessionDescription

/*class RtcSdpObserver(private val ctx: Rtc) : SdpObserver {
    override fun onSetFailure(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSetSuccess() {
        if(ctx.peerConnection.remoteDescription.type == SessionDescription.Type.OFFER){
            ctx.peerConnection.createAnswer(this, ctx.audioConstraints)
        }
    }

    override fun onCreateSuccess(p0: SessionDescription?) {
        ctx.peerConnection.setLocalDescription(this, p0)
        HttpExecute.BuildAPI().SetLocalDesc(ctx.peerConnection.localDescription).execute()
    }

    override fun onCreateFailure(p0: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}*/