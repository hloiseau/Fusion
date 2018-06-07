var pc1;
var pc2;
class rtcService {
    
    constructor() {
    }

    setpc1(pc1){
        this.pc1 = pc1;
    }
    setpc2(pc2){
        this.pc2 = pc2
    }
    trace(arg) {
        var now = (window.performance.now() / 1000).toFixed(3);
        console.log(now + ': ', arg);
    }
    onIceCandidate(pc, event) {
        var otherPc = this.getOtherPc(pc)
        otherPc.addIceCandidate(event.candidate)
            .then(
                function () {
                    //this.onAddIceCandidateSuccess();
                },
                function (err) {
                    //this.onAddIceCandidateError(err);
                }
            );
        this.trace(this.getName(pc, pc1) + ' ICE candidate: \n' + (event.candidate ?
            event.candidate.candidate : '(null)'));
    }
    onAddIceCandidateSuccess() {
        this.trace('AddIceCandidate success.');
    }

    onAddIceCandidateError(error) {
        this.trace('Failed to add ICE Candidate: ' + error.toString());
    }

    getOtherPc(pc) {
        return (pc === this.pc1) ? this.pc2 : this.pc1;
    }
    getName(pc, pc1) {
        return (pc === pc1) ? 'pc1' : 'pc2';
    }

    forceChosenAudioCodec(sdp, value) {
        return this.maybePreferCodec(sdp, 'audio', 'send', value);
    }

    maybePreferCodec(sdp, type, dir, codec) {
        var str = type + ' ' + dir + ' codec';
        if (codec === '') {
            this.trace('No preference on ' + str + '.');
            return sdp;
        }

        this.trace('Prefer ' + str + ': ' + codec);

        var sdpLines = sdp.split('\r\n');

        // Search for m line.
        var mLineIndex = this.findLine(sdpLines, 'm=', type);
        if (mLineIndex === null) {
            return sdp;
        }

        // If the codec is available, set it as the default in m line.
        var codecIndex = this.findLine(sdpLines, 'a=rtpmap', codec);
        console.log('codecIndex', codecIndex);
        if (codecIndex) {
            var payload = this.getCodecPayloadType(sdpLines[codecIndex]);
            if (payload) {
                sdpLines[mLineIndex] = this.setDefaultCodec(sdpLines[mLineIndex], payload);
            }
        }

        sdp = sdpLines.join('\r\n');
        return sdp;
    }
    // Find the line in sdpLines that starts with |prefix|, and, if specified,
    // contains |substr| (case-insensitive search).
    findLine(sdpLines, prefix, substr) {
        return this.findLineInRange(sdpLines, 0, -1, prefix, substr);
    }

    // Find the line in sdpLines[startLine...endLine - 1] that starts with |prefix|
    // and, if specified, contains |substr| (case-insensitive search).
    findLineInRange(sdpLines, startLine, endLine, prefix, substr) {
        var realEndLine = endLine !== -1 ? endLine : sdpLines.length;
        for (var i = startLine; i < realEndLine; ++i) {
            if (sdpLines[i].indexOf(prefix) === 0) {
                if (!substr ||
                    sdpLines[i].toLowerCase().indexOf(substr.toLowerCase()) !== -1) {
                    return i;
                }
            }
        }
        return null;
    }
    getCodecPayloadType(sdpLine) {
        var pattern = new RegExp('a=rtpmap:(\\d+) \\w+\\/\\d+');
        var result = sdpLine.match(pattern);
        return (result && result.length === 2) ? result[1] : null;
    }
    setDefaultCodec(mLine, payload) {
        var elements = mLine.split(' ');

        // Just copy the first three parameters; codec order starts on fourth.
        var newLine = elements.slice(0, 3);

        // Put target payload first and copy in the rest.
        newLine.push(payload);
        for (var i = 3; i < elements.length; i++) {
            if (elements[i] !== payload) {
                newLine.push(elements[i]);
            }
        }
        return newLine.join(' ');
    }

}

export default new rtcService(pc1, pc2)