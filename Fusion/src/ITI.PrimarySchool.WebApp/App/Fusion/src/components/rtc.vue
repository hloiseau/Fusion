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
            <button v-on:click="startTalk()" id="callButton">Call</button>
            <!--<button v-on:click="hangup()" id="hangupButton">Hang Up</button>-->
        </div>
    </div>
</template>

<script src="https://webrtc.github.io/adapter/adapter-latest.js"></script>

<script>
    import service from "../services/rtcService";
    import RtcApiService from "../services/rtcApiService";

    export default {
        data() {
            return {
                audio1: null,
                audio2: null,
                callButton: null,
                hangupButton: null,
                codecSelector: null,

                pc: null,
                localStream: null,

                lastResult: null,

                offerOptions: {
                    offerToReceiveAudio: 1,
                    offerToReceiveVideo: 0,
                    voiceActivityDetection: false
                },
                connection: null
            }
        },
        mounted() {
            this.audio1 = document.querySelector('audio#audio1')
            this.audio2 = document.querySelector('audio#audio2')
            this.callButton = document.querySelector('button#callButton')
            this.hangupButton = document.querySelector('button#hangupButton')
            this.codecSelector = document.querySelector('select#codec')
            //this.hangupButton.disabled = true
            var notif = null
            var signalR = require("@aspnet/signalr")
            this.connection = new signalR.HubConnectionBuilder().withUrl("/vue").configureLogging(signalR.LogLevel.Information)
                .build()
            var pc = this.pc
            this.connection.on("sdp", sdp => {
                if (this.pc == null) {
                    this.startTalk()
                }
                this.pc.setRemoteDescription(new RTCSessionDescription(sdp), function () {
                    if (this.pc.remoteDescription.type == 'offer') {
                        this.pc.createAnswer(this.offerCreated)
                    }
                })
            });
            this.connection.on("candidate", candidate => {
                console.log(candidate)
                if (this.pc == null) {
                    this.startTalk()
                }
                this.pc.addIceCandidate(new RTCIceCandidate(candidate))
            });
            this.connection.start().catch(err => console.log(err.toString()));
        },
        methods: {
            trace(arg) {
                var now = (window.performance.now() / 1000).toFixed(3);
                console.log(now + ': ', arg);
            },
            offerCreated(desc) {
                var pc = this.pc
                pc.setLocalDescription(desc, function () {
                    RtcApiService.postDescAsync(
                        desc
                    );
                }, this.trace("post sdp"))
                this.pc = pc

            },
            startTalk() {
                this.pc = new RTCPeerConnection()
                this.pc.onicecandidate = function (evt) {
                    if (evt.candidate) {
                        RtcApiService.postCandAsync(evt.candidate)
                    }
                }
                var pc = this.pc
                var offerCreated = this.offerCreated
                this.pc.onnegotiationneeded = function () {
                    pc.createOffer(offerCreated, function () {
                        console.log("create offer")
                    })
                }
                this.pc = pc
                var audio2 = this.audio2
                this.pc.onaddstream = function (e) {
                    audio2.srcObject = stream
                }
                this.audio2 = audio2
                var audio1 = this.audio1
                pc = this.pc
                navigator.getUserMedia({
                    audio: true,
                    video: false
                }, function (stream) {
                    audio1.srcObject = stream
                    pc.addStream(stream)
                }, function (err) {
                    console.log(err)
                })
            }
        }
    }
</script>

<style lang="scss">
</style>