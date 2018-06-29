<template>
    <div>
        <div></div>
        <div id="audio" class="audio">
            <div id="buttons">
                <button v-on:click="startTalk()" id="callButton" disabled>Call</button>
                <button v-on:click="hangup()" id="hangupButton" disabled>Hang Up</button>
            </div>
            <div>
                <div id="audio2">

                </div>
            </div>
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
                    offerToReceiveAudio: 0,
                    offerToReceiveVideo: 1,
                    voiceActivityDetection: false
                },
                connection: null
            }
        },
        mounted() {
            this.audio1 = document.querySelector('div#audio1')
            this.audio2 = document.querySelector('div#audio2')
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
            this.phone.ready(this.readying)

            this.phone.receive(this.receiving)
        },
        methods: {
            startTalk() {
                console.log("calling")
                this.callButton.disabled = true
                this.hangupButton.disabled = false
                this.session = this.phone.dial('test')
            },
            readying() {
                this.callButton.disabled = false
                console.log("ready")
            },
            hangup() {
                this.callButton.disabled = false
                this.hangupButton.disabled = true
                this.phone.hangup()
                var lol = document.getElementById('audio2')
                lol.innerHTML = ''
            },
            receiving(session) {
                session.connected(this.connecting)
            },
            connecting(session) {
                console.log("receiving things...")
                this.phone.$('audio2').appendChild(session.video);
            }
        }
    }
</script>

<style lang="scss">
    .audio {
        margin-top: 5%
    }
</style>