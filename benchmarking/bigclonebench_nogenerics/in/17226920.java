


class c17226920 {

    public void setUrl(URL url) throws PDFRuntimeException, PDFSecurityRuntimeException, IORuntimeException {
        InputStream in = null;
        try {
            URLConnection urlConnection = url.openConnection();
            in = urlConnection.getInputStream();
            String pathOrURL = url.toString();
            setInputStream(in, pathOrURL);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

}
