package fr.intechinfo.fusionandroid

import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Handler
import android.os.Looper
import android.util.Log
import me.kevingleason.pnwebrtc.PnPeer
import me.kevingleason.pnwebrtc.PnRTCListener
import org.webrtc.AudioTrack
import org.webrtc.MediaStream
import org.webrtc.VideoRenderer
import org.webrtc.VideoRendererGui
import android.widget.Toast



class RtcListener(val context: Context) : PnRTCListener() {
    override fun onLocalStream(localStream: MediaStream?) {
        super.onLocalStream(localStream)
        val mainHandler = Handler(context.mainLooper)
        val myRunnable = object : Runnable {
            override fun run() {
                val localRender = VideoRendererGui.createGuiRenderer(0,0,100,100, VideoRendererGui.ScalingType.SCALE_ASPECT_FILL, true)
                if(localStream!!.videoTracks.size==0) return
                localStream.videoTracks[0].addRenderer(VideoRenderer(localRender))
            }
        }
        mainHandler.post(myRunnable)


    }

    override fun onAddRemoteStream(remoteStream: MediaStream?, peer: PnPeer?) {
        super.onAddRemoteStream(remoteStream, peer)
        val mainHandler = Handler(context.mainLooper)
        val myRunnable = object : Runnable {
            override fun run() {
                val remoteRender = VideoRendererGui.createGuiRenderer(0,0,100,100, VideoRendererGui.ScalingType.SCALE_ASPECT_FILL, false)
                val localRender = VideoRendererGui.createGuiRenderer(0,0,100,100, VideoRendererGui.ScalingType.SCALE_ASPECT_FILL, true)

                Toast.makeText(context , "Connected to " + peer!!.id, Toast.LENGTH_SHORT).show()
                try {
                    if (remoteStream!!.videoTracks.size == 0) return
                    remoteStream.videoTracks[0].addRenderer(VideoRenderer(remoteRender))
                    VideoRendererGui.update(remoteRender, 0, 0, 100, 100, VideoRendererGui.ScalingType.SCALE_ASPECT_FILL, false)
                    VideoRendererGui.update(localRender, 72, 72, 25, 25, VideoRendererGui.ScalingType.SCALE_ASPECT_FIT, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        mainHandler.post(myRunnable)
    }
}