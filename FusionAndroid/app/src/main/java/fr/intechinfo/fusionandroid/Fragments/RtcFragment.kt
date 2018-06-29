package fr.intechinfo.fusionandroid.Fragments

import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import fr.intechinfo.fusionandroid.R
import fr.intechinfo.fusionandroid.Rtc
import org.webrtc.VideoRendererGui


class RtcFragment : Fragment() {

    lateinit var glSurfaceView : GLSurfaceView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootview = inflater.inflate(R.layout.fragment_rtc, container, false)
        glSurfaceView = rootview.findViewById<GLSurfaceView>(R.id.gl_surface)
        VideoRendererGui.setView(glSurfaceView, null)
        return rootview

    }

    override fun onPause() {
        super.onPause()
        glSurfaceView.onPause()
        Rtc.instance.localVideoSource.stop()
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView.onResume()
        Rtc.instance.localVideoSource.restart()
    }

    override fun onDestroy() {
        super.onDestroy()
        Rtc.instance.localVideoSource.stop()
        Rtc.instance.pnRTCClient.onDestroy()
    }


    companion object {
        // TODO: Rename and change types and number of parameters
        fun newInstance(): RtcFragment {
            return RtcFragment()
        }
    }
}