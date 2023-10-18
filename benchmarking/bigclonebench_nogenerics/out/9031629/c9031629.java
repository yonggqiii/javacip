class c9031629 {

    public static byte[] readFromURI(URI uri) throws IORuntimeException {
        if (uri.toString().contains("http:")) {
            URL url = uri.toURL();
            URLConnection urlConnection = url.openConnection();
            int length = urlConnection.getContentLength();
            System.out.println("length of content in URL = " + length);
            if (length > -1) {
                byte[] pureContent = new byte[length];
                DataInputStream dis = new DataInputStream(urlConnection.getInputStream());
                dis.readFully(pureContent, 0, length);
                dis.close();
                return pureContent;
            } else {
                throw new IORuntimeException("Unable to determine the content-length of the document pointed at " + url.toString());
            }
        } else {
            return JavaCIPUnknownScope.readWholeFile(uri).getBytes("UTF-8");
        }
    }
}
