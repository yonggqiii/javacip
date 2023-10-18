class c16223920 {

    public AudioFileFormat getAudioFileFormat(URL url) throws UnsupportedAudioFileRuntimeException, IORuntimeException {
        InputStream urlStream = url.openStream();
        AudioFileFormat fileFormat = null;
        try {
            fileFormat = JavaCIPUnknownScope.getFMT(urlStream, false);
        } finally {
            urlStream.close();
        }
        return fileFormat;
    }
}
