class c8943484 {

    public BufferedImage getImage(String urlStr) throws IORuntimeException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (JavaCIPUnknownScope.transport instanceof REST) {
            if (((REST) JavaCIPUnknownScope.transport).isProxyAuth()) {
                conn.setRequestProperty("Proxy-Authorization", "Basic " + ((REST) JavaCIPUnknownScope.transport).getProxyCredentials());
            }
        }
        conn.connect();
        InputStream in = null;
        try {
            in = conn.getInputStream();
            return ImageIO.read(in);
        } finally {
            IOUtilities.close(in);
        }
    }
}
