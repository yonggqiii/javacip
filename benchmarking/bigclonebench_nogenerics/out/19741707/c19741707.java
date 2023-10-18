class c19741707 {

    protected BufferedReader getBufferedReader(InputSource input) throws IORuntimeException, SAXRuntimeException {
        BufferedReader br = null;
        if (input.getCharacterStream() != null) {
            br = new BufferedReader(input.getCharacterStream());
        } else if (input.getByteStream() != null) {
            br = new BufferedReader(new InputStreamReader(input.getByteStream()));
        } else if (input.getSystemId() != null) {
            URL url = new URL(input.getSystemId());
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } else {
            throw new SAXRuntimeException("Invalid InputSource!");
        }
        return br;
    }
}
