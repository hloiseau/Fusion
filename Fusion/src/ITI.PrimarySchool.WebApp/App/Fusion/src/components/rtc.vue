<template>
    <div>
        <div id="audio">
            <div>
                <div class="label">Local audio:</div>
                <audio id="audio1" autoplay controls muted></audio>
            </div>
            <div>
                <div class="label">Remote audio:</div>
                <audio id="audio2" autoplay controls></audio>
            </div>
        </div>

        <div id="buttons">
            <select id="codec">
                <!-- Codec values are matched with how they appear in the SDP.
        For instance, opus matches opus/48000/2 in Chrome, and ISAC/16000
        matches 16K iSAC (but not 32K iSAC). -->
                <option value="opus">Opus</option>
                <option value="ISAC">iSAC 16K</option>
                <option value="G722">G722</option>
                <option value="PCMU">PCMU</option>
            </select>
            <button v-on:click="call()" id="callButton">Call</button>
            <button v-on:click="hangup()" id="hangupButton">Hang Up</button>
        </div>
    </div>
</template>

<script src="https://webrtc.github.io/adapter/adapter-latest.js"></script>

<script>
    import service from "../services/rtcService";
    export default {
        data() {
            return {
                audio2: null,
                callButton: null,
                hangupButton: null,
                codecSelector: null,

                pc1: null,
                pc2: null,
                localStream: null,

                lastResult: null,

                offerOptions: {
                    offerToReceiveAudio: 1,
                    offerToReceiveVideo: 0,
                    voiceActivityDetection: false
                },
            }
        },
        mounted() {
            this.audio2 = document.querySelector('audio#audio2'),
                this.callButton = document.querySelector('button#callButton'),
                this.hangupButton = document.querySelector('button#hangupButton'),
                this.codecSelector = document.querySelector('select#codec'),
                this.hangupButton.disabled = true
        },
        methods: {
            trace(arg) {
                var now = (window.performance.now() / 1000).toFixed(3);
                console.log(now + ': ', arg);
            },
            gotStream(stream) {
                this.hangupButton.disabled = false;
                this.trace('Received local stream');
                this.localStream = stream;
                var pc = this.pc1;
                var audioTracks = this.localStream.getAudioTracks();
                if (audioTracks.length > 0) {
                    this.trace('Using Audio device: ' + audioTracks[0].label);
                }
                this.localStream.getTracks().forEach(
                    function (track) {
                        pc.addTrack(
                            track,
                            stream
                        );
                    }
                );
                this.trace('Adding Local Stream to peer connection');

                this.pc1.createOffer(
                    this.offerOptions
                ).then(
                    this.gotDescription1,
                    this.onCreateSessionDescriptionError
                );
            },
            onCreateSessionDescriptionError(error) {
                this.trace('Failed to create session description: ' + error.toString());
            },
            call() {
                this.callButton.disabled = true;
                this.codecSelector.disabled = true;
                this.trace('Starting call');
                this.pc1 = new RTCPeerConnection(null);
                this.trace('Created local peer connection object pc1');
                var pc1 = this.pc1;
                var pc2 = this.pc2;
                this.pc1.onicecandidate = function (e) {
                    service.setpc1(pc1);
                    service.setpc2(pc2);
                    service.onIceCandidate(pc1, e);
                };
                this.pc2 = new RTCPeerConnection(null);
                this.trace('Created remote peer connection object pc2');
                pc1 = this.pc1;
                pc2 = this.pc2
                this.pc2.onicecandidate = function (e) {
                    service.setpc1(pc1);
                    service.setpc2(pc2);
                    service.onIceCandidate(pc2, e);
                };
                this.pc2.ontrack = this.gotRemoteStream;
                this.trace('Requesting local stream');
                navigator.mediaDevices.getUserMedia({
                        audio: true,
                        video: false
                    })
                    .then(this.gotStream)
                    .catch(function (e) {
                        console.log('erreur getuser:' + e.toString());
                    });
            },
            gotDescription1(desc) {
                this.trace('Offer from pc1 \n' + desc.sdp);
                var desc2 = service.forceChosenAudioCodec(desc.sdp, this.codecSelector.value);
                var pc = this.pc2;
                this.pc1.setLocalDescription(desc).then(
                    this.setDesc(pc, desc2, desc),
                    this.onSetSessionDescriptionError
                );
            },
            setDesc(pc, desc2, desc) {
                desc.sdp = desc2;
                pc.setRemoteDescription(desc).then(
                    this.CreateAnswer(pc),
                    // this.onSetSessionDescriptionError
                );
            },
            CreateAnswer(pc) {
                pc.createAnswer().then(
                    this.gotDescription2,
                    this.onCreateSessionDescriptionError
                );

            },
            gotDescription2(desc) {
                this.trace('Answer from pc2 \n' + desc.sdp);
                this.pc2.setLocalDescription(desc).then(
                    this.setDesc2(desc),
                    this.onSetSessionDescriptionError
                );
            },
            setDesc2(desc) {
                desc.sdp = service.forceChosenAudioCodec(desc.sdp);
                this.pc1.setRemoteDescription(desc).then(
                    function () {},
                    this.onSetSessionDescriptionError
                );
            },
            hangup() {
                this.trace('Ending call');
                this.localStream.getTracks().forEach(function (track) {
                    track.stop();
                });
                this.pc1.close();
                this.pc2.close();
                this.pc1 = null;
                this.pc2 = null;
                this.hangupButton.disabled = true;
                this.callButton.disabled = false;
                this.codecSelector.disabled = false;
            },
            gotRemoteStream(e) {
                if (this.audio2.srcObject !== e.streams[0]) {
                    this.audio2.srcObject = e.streams[0];
                    this.trace('Received remote stream');
                }
            },
            onSetSessionDescriptionError(error) {
                this.trace('Failed to set session description: ' + error.toString());
            }
        }
    }
</script>

<style lang="scss">
</style>