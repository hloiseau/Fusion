package fr.intechinfo.fusionandroid

import android.content.Context
import org.webrtc.*
import org.webrtc.PeerConnection
import org.webrtc.PeerConnection.IceServer
import org.webrtc.MediaConstraints
import com.google.firebase.components.Dependency.optional





class Rtc private constructor() {

    private lateinit var peerConnectionFactory: PeerConnectionFactory
    lateinit var mediaStream: MediaStream
    var peerConnection: PeerConnection? = null
    private val observer = RtcObserver()
    val sdpObserver = RtcSdpObserver()
    val audioConstraints = MediaConstraints()

    private object Holder{val INSTANCE = Rtc()}
    fun initRtcAudio(context: Context) {
        PeerConnectionFactory.initializeAndroidGlobals(context, true, false, false, null)
        peerConnectionFactory = PeerConnectionFactory()
        mediaStream = peerConnectionFactory.createLocalMediaStream("stream1")
        audioConstraints.optional.add(MediaConstraints.KeyValuePair(
                "DtlsSrtpKeyAgreement", "true"))
        audioConstraints.mandatory.add(MediaConstraints.KeyValuePair(
                "OfferToReceiveAudio", "true"))
        audioConstraints.mandatory.add(MediaConstraints.KeyValuePair(
                "OfferToReceiveVideo", "false"))
        val audioSource = peerConnectionFactory.createAudioSource(audioConstraints)
        val audioTrack = peerConnectionFactory.createAudioTrack("comm1", audioSource)
        mediaStream.addTrack(audioTrack)
        val iceServers = ArrayList<PeerConnection.IceServer>()
        peerConnection = peerConnectionFactory.createPeerConnection(iceServers, audioConstraints, observer)
    }

    fun negotiationNeeded(){
        peerConnection!!.createOffer(sdpObserver, audioConstraints)
    }

    companion object {
        val instance: Rtc by lazy { Holder.INSTANCE }

    }
}