package fr.intechinfo.fusionandroid

import android.content.Context
import android.content.Intent
import android.provider.SyncStateContract
import android.util.Log
import com.google.android.gms.common.internal.Constants
import org.webrtc.*
import org.webrtc.PeerConnection
import org.webrtc.PeerConnection.IceServer
import org.webrtc.MediaConstraints
import com.google.firebase.components.Dependency.optional
import me.kevingleason.pnwebrtc.PnRTCClient
import me.kevingleason.pnwebrtc.PnSignalingParams

class Rtc private constructor() {

    lateinit var peerConnectionFactory: PeerConnectionFactory
    lateinit var mediaStream: MediaStream
    var peerConnection: PeerConnection? = null
    val audioConstraints = MediaConstraints()
    lateinit var pnRTCClient: PnRTCClient

    private object Holder{val INSTANCE = Rtc()}
    fun initRtcAudio(context: Context) {
        Log.d("Lord", "of the rings")
        PeerConnectionFactory.initializeAndroidGlobals(context, true, false, false, null)
        peerConnectionFactory = PeerConnectionFactory()
        pnRTCClient = PnRTCClient(fr.intechinfo.fusionandroid.Constants.PUB_KEY, fr.intechinfo.fusionandroid.Constants.SUB_KEY )
        audioConstraints.optional.add(MediaConstraints.KeyValuePair(
                "DtlsSrtpKeyAgreement", "true"))
        audioConstraints.mandatory.add(MediaConstraints.KeyValuePair(
                "OfferToReceiveAudio", "true"))
        audioConstraints.mandatory.add(MediaConstraints.KeyValuePair(
                "OfferToReceiveVideo", "false"))

        val params = PnSignalingParams(audioConstraints, null, null)
        pnRTCClient.setSignalParams(params)
        pnRTCClient.attachRTCListener(RtcListener())
        mediaStream = peerConnectionFactory.createLocalMediaStream("stream1")
        val audioSource = peerConnectionFactory.createAudioSource(this.pnRTCClient.audioConstraints())
        val audioTrack = peerConnectionFactory.createAudioTrack("comm1", audioSource)
        mediaStream.addTrack(audioTrack)
        pnRTCClient.attachLocalMediaStream(mediaStream)
        pnRTCClient.setMaxConnections(1)
        pnRTCClient.listenOn("test-stdby")
    }

    companion object {
        val instance: Rtc by lazy { Holder.INSTANCE }

    }
}