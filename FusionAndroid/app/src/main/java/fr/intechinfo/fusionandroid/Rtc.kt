package fr.intechinfo.fusionandroid

import android.content.Context
import org.webrtc.*

class Rtc {

    /*private val peerConnectionFactory = PeerConnectionFactory()
    var mediaStream = peerConnectionFactory.createLocalMediaStream("stream1")
    lateinit var peerConnection: PeerConnection
    private val observer = RtcObserver(this)
    val sdpObserver = RtcSdpObserver(this)
    val audioConstraints = MediaConstraints()
    fun initRtcAudio(context: Context) {
        PeerConnectionFactory.initializeAndroidGlobals(context, true, false, false, null)
        val constraint1 = MediaConstraints.KeyValuePair("offerToReceiveAudio", "true")
        val constraint2 = MediaConstraints.KeyValuePair("offerToReceiveVideo", "false")
        audioConstraints.mandatory.add(constraint1)
        audioConstraints.mandatory.add(constraint2)
        val audioSource = peerConnectionFactory.createAudioSource(audioConstraints)
        val audioTrack = peerConnectionFactory.createAudioTrack("comm1", audioSource)
        mediaStream.addTrack(audioTrack)
        val rtcConfig = PeerConnection.RTCConfiguration(null)
        peerConnection = peerConnectionFactory.createPeerConnection(rtcConfig, audioConstraints, observer)
    }

    fun negotiationNeeded(){
        peerConnection.createOffer(sdpObserver, audioConstraints)
    }*/

}