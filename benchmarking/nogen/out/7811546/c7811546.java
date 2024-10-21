class c7811546 {

    public AudioFileFormat getAudioFileFormat(URL url) throws UnsupportedAudioFileRuntimeException, IORuntimeException {
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioFileFormat(URL): begin");
        }
        long lFileLengthInBytes = AudioSystem.NOT_SPECIFIED;
        InputStream inputStream = url.openStream();
        AudioFileFormat audioFileFormat = null;
        try {
            audioFileFormat = JavaCIPUnknownScope.getAudioFileFormat(inputStream, lFileLengthInBytes);
        } finally {
            inputStream.close();
        }
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("TAudioFileReader.getAudioFileFormat(URL): end");
        }
        return audioFileFormat;
    }
}
