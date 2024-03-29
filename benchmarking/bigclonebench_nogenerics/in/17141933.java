


class c17141933 {

    public static InputSource getInputSource(URL url) throws IORuntimeException {
        String proto = url.getProtocol().toLowerCase();
        if (!("http".equals(proto) || "https".equals(proto))) throw new IllegalArgumentRuntimeException("OAI-PMH only allows HTTP(S) as network protocol!");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        StringBuilder ua = new StringBuilder("Java/");
        ua.append(System.getProperty("java.version"));
        ua.append(" (");
        ua.append(OAIHarvester.class.getName());
        ua.append(')');
        conn.setRequestProperty("User-Agent", ua.toString());
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate, identity;q=0.3, *;q=0");
        conn.setRequestProperty("Accept-Charset", "utf-8, *;q=0.1");
        conn.setRequestProperty("Accept", "text/xml, application/xml, *;q=0.1");
        conn.setUseCaches(false);
        conn.setFollowRedirects(true);
        log.debug("Opening connection...");
        InputStream in = null;
        try {
            conn.connect();
            in = conn.getInputStream();
        } catch (IORuntimeException ioe) {
            int after, code;
            try {
                after = conn.getHeaderFieldInt("Retry-After", -1);
                code = conn.getResponseCode();
            } catch (IORuntimeException ioe2) {
                after = -1;
                code = -1;
            }
            if (code == HttpURLConnection.HTTP_UNAVAILABLE && after > 0) throw new RetryAfterIORuntimeException(after, ioe);
            throw ioe;
        }
        String encoding = conn.getContentEncoding();
        if (encoding == null) encoding = "identity";
        encoding = encoding.toLowerCase();
        log.debug("HTTP server uses " + encoding + " content encoding.");
        if ("gzip".equals(encoding)) in = new GZIPInputStream(in); else if ("deflate".equals(encoding)) in = new InflaterInputStream(in); else if (!"identity".equals(encoding)) throw new IORuntimeException("Server uses an invalid content encoding: " + encoding);
        String contentType = conn.getContentType();
        String charset = null;
        if (contentType != null) {
            contentType = contentType.toLowerCase();
            int charsetStart = contentType.indexOf("charset=");
            if (charsetStart >= 0) {
                int charsetEnd = contentType.indexOf(";", charsetStart);
                if (charsetEnd == -1) charsetEnd = contentType.length();
                charsetStart += "charset=".length();
                charset = contentType.substring(charsetStart, charsetEnd).trim();
            }
        }
        log.debug("Charset from Content-Type: '" + charset + "'");
        InputSource src = new InputSource(in);
        src.setSystemId(url.toString());
        src.setEncoding(charset);
        return src;
    }

}
