


class c3704402 {

    public AudioFileFormat getAudioFileFormat(URL url) throws UnsupportedAudioFileRuntimeException, IORuntimeException {
        InputStream stream = url.openStream();
        AudioFileFormat format;
        try {
            format = getAudioFileFormat(new BufferedInputStream(stream));
        } finally {
            stream.close();
        }
        return format;
    }

}
