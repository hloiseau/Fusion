package fr.intechinfo.fusionandroid

import android.content.Context
import android.content.Intent
import android.media.AudioManager
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
import org.webrtc.VideoCapturerAndroid
import org.webrtc.VideoTrack





class Rtc private constructor() {

    lateinit var peerConnectionFactory: PeerConnectionFactory
    lateinit var mediaStream: MediaStream
    var peerConnection: PeerConnection? = null
    val audioConstraints = MediaConstraints()
    lateinit var pnRTCClient: PnRTCClient

    private object Holder{val INSTANCE = Rtc()}
    fun initRtcAudio(context: Context) {
        PeerConnectionFactory.initializeAndroidGlobals(context, false, true, true, null)
        peerConnectionFactory = PeerConnectionFactory()
        pnRTCClient = PnRTCClient(fr.intechinfo.fusionandroid.Constants.PUB_KEY, fr.intechinfo.fusionandroid.Constants.SUB_KEY, "test" )
        audioConstraints.optional.add(MediaConstraints.KeyValuePair(
                "DtlsSrtpKeyAgreement", "true"))
        audioConstraints.mandatory.add(MediaConstraints.KeyValuePair(
                "OfferToReceiveAudio", "false"))
        audioConstraints.mandatory.add(MediaConstraints.KeyValuePair(
                "OfferToReceiveVideo", "true"))

        val params = PnSignalingParams(audioConstraints, null, null)
        pnRTCClient.setSignalParams(params)
        pnRTCClient.attachRTCListener(RtcListener())
        mediaStream = peerConnectionFactory.createLocalMediaStream("stream1")
        //val audioSource = peerConnectionFactory.createAudioSource(this.pnRTCClient.audioConstraints())
        //val audioTrack = peerConnectionFactory.createAudioTrack("comm1", audioSource)
        pnRTCClient.attachLocalMediaStream(mediaStream)

        val camNumber = VideoCapturerAndroid.getDeviceCount()
        val frontFacingCam = VideoCapturerAndroid.getNameOfFrontFacingDevice()
        val backFacingCam = VideoCapturerAndroid.getNameOfBackFacingDevice()
        // Creates a VideoCapturerAndroid instance for the device name
        val capturer = VideoCapturerAndroid.create(frontFacingCam)

        // First create a Video Source, then we can make a Video Track
        val localVideoSource = peerConnectionFactory.createVideoSource(capturer, this.pnRTCClient.videoConstraints())
        val localVideoTrack = peerConnectionFactory.createVideoTrack("video", localVideoSource)

        mediaStream.addTrack(localVideoTrack)
        val  audioManager =  context.getSystemService(Context.AUDIO_SERVICE)as AudioManager
        audioManager.mode = AudioManager.MODE_IN_COMMUNICATION
        pnRTCClient.setMaxConnections(1)
        pnRTCClient.listenOn("test")
    }



    companion object {
        val instance: Rtc by lazy { Holder.INSTANCE }

    }
}