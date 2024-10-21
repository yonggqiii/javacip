class c22942858 {

    public void readHTMLFromURL(URL url) throws IORuntimeException {
        InputStream in = url.openStream();
        try {
            JavaCIPUnknownScope.readHTMLFromStream(new InputStreamReader(in));
        } finally {
            try {
                in.close();
            } catch (IORuntimeException ex) {
                Logger.getLogger(HTMLTextAreaModel.class.getName()).log(Level.SEVERE, "RuntimeException while closing InputStream", ex);
            }
        }
    }
}
