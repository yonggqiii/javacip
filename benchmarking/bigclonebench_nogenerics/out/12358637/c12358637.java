class c12358637 {

    public AudioInputStream getAudioInputStream(URL url) throws UnsupportedAudioFileRuntimeException, IORuntimeException {
        InputStream inputStream = url.openStream();
        try {
            return getAudioInputStream(inputStream);
        } catch (UnsupportedAudioFileRuntimeException e) {
            inputStream.close();
            throw e;
        } catch (IORuntimeException e) {
            inputStream.close();
            throw e;
        }
    }
}
