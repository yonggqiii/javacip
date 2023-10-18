class c16604588 {

    public static AudioFileFormat getAudioFileFormat(URL url) throws UnsupportedAudioFileRuntimeException, IORuntimeException {
        InputStream inputStream = null;
        if (JavaCIPUnknownScope.useragent != null) {
            URLConnection myCon = url.openConnection();
            myCon.setUseCaches(false);
            myCon.setDoInput(true);
            myCon.setDoOutput(true);
            myCon.setAllowUserInteraction(false);
            myCon.setRequestProperty("User-Agent", JavaCIPUnknownScope.useragent);
            myCon.setRequestProperty("Accept", "*/*");
            myCon.setRequestProperty("Icy-Metadata", "1");
            myCon.setRequestProperty("Connection", "close");
            inputStream = new BufferedInputStream(myCon.getInputStream());
        } else {
            inputStream = new BufferedInputStream(url.openStream());
        }
        try {
            if (JavaCIPUnknownScope.DEBUG == true) {
                System.err.println("Using AppletMpegSPIWorkaround to get codec (AudioFileFormat:url)");
            }
            return JavaCIPUnknownScope.getAudioFileFormatForUrl(inputStream);
        } finally {
            inputStream.close();
        }
    }
}
