class c15194834 {

    public AudioFileFormat getAudioFileFormat(URL url) throws UnsupportedAudioFileRuntimeException, IORuntimeException {
        if (TDebug.TraceAudioFileReader) {
            TDebug.out("getAudioFileFormat(URL url)");
        }
        InputStream inputStream = url.openStream();
        try {
            return getAudioFileFormat(inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
