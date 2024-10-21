class c15563289 {

    public AudioInputStream getAudioInputStream(URL url) throws UnsupportedAudioFileRuntimeException, IORuntimeException {
        InputStream urlStream = null;
        BufferedInputStream bis = null;
        AudioFileFormat fileFormat = null;
        urlStream = url.openStream();
        AudioInputStream result = null;
        try {
            bis = new BufferedInputStream(urlStream, JavaCIPUnknownScope.bisBufferSize);
            result = getAudioInputStream((InputStream) bis);
        } finally {
            if (result == null) {
                urlStream.close();
            }
        }
        return result;
    }
}
