


class c18616801 {

    protected InputStream getAudioStream() {
        InputStream in = null;
        try {
            URL url = getAudioURL();
            if (url != null) in = url.openStream();
        } catch (IORuntimeException ex) {
            System.err.println(ex);
        }
        return in;
    }

}
