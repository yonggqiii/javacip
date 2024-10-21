class c16223923 {

    public AudioInputStream getAudioInputStream(URL url) throws UnsupportedAudioFileRuntimeException, IORuntimeException {
        InputStream urlStream = url.openStream();
        AudioFileFormat fileFormat = null;
        try {
            fileFormat = JavaCIPUnknownScope.getFMT(urlStream, false);
        } finally {
            if (fileFormat == null) {
                urlStream.close();
            }
        }
        return new AudioInputStream(urlStream, fileFormat.getFormat(), fileFormat.getFrameLength());
    }
}
