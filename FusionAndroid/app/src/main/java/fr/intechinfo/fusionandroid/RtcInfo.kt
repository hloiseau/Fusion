package fr.intechinfo.fusionandroid

import org.webrtc.IceCandidate
import org.webrtc.SessionDescription


class RtcInfo(private val IceCandidate: IceCandidate?, private val Desc: SessionDescription?) {

    fun GetCandidate(): IceCandidate? {
        return IceCandidate
    }

    fun GetDesc(): SessionDescription? {
        return Desc
    }



}
