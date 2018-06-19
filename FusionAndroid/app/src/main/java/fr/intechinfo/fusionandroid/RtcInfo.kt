package fr.intechinfo.fusionandroid


class RtcInfo(private val candidate: String?, private val sdp: String?) {

    fun GetCandidate(): String? {
        return candidate
    }

    fun GetSdp(): String? {
        return sdp
    }



}
