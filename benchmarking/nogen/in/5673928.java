


class c5673928 {

    void loadImage(Frame frame, URL url) throws RuntimeException {
        URLConnection conn = url.openConnection();
        String mimeType = conn.getContentType();
        long length = conn.getContentLength();
        InputStream is = conn.getInputStream();
        loadImage(frame, is, length, mimeType);
    }

}
