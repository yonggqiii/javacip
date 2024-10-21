


class c21741649 {

    private InputStream getInputStream() throws URISyntaxRuntimeException, MalformedURLRuntimeException, IORuntimeException {
        InputStream inStream = null;
        try {
            URL url = new URI(wsdlFile).toURL();
            URLConnection connection = url.openConnection();
            connection.connect();
            inStream = connection.getInputStream();
        } catch (IllegalArgumentRuntimeException ex) {
            inStream = new FileInputStream(wsdlFile);
        }
        return inStream;
    }

}
