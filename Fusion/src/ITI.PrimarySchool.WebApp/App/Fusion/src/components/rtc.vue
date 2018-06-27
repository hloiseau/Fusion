<template>
    <div>
        <div id="audio">
            <div>
                <div class="label">Local audio:</div>
                <video id="audio1" autoplay controls muted></video>
            </div>
            <div>
                <div class="label">Remote audio:</div>
                <video id="audio2" autoplay controls></video>
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

                session: null,
                phone: null,

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
            var ready = false
            this.phone = PHONE({
                number: '1234',
                autocam: true,
                publish_key: 'pub-c-b840de4f-c044-4bc9-a823-c21724913221',
                subscribe_key: 'sub-c-55b78e0a-745b-11e8-902b-b2b3cb3accda',
                ssl: false,
                media: {
                    audio: false,
                    video: true
                }
            })
            this.phone.ready(function () {
                ready = true
                console.log(ready)
            });
            var audio = this.audio2
            this.phone.receive(function (session) {
                
                session.connected(function (session) {
                    console.log("receiving things...")
                    audio.srcObject = session.video
                })
            })
            this.audio2 = audio
        },
        methods: {
            startTalk() {
                console.log("calling")
                this.session = this.phone.dial('test')
            }
        }
    }
</script>

<style lang="scss">
</style>