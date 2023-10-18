class c7604737 {

    public String get(String url) {
        String buf = null;
        StringBuilder resultBuffer = new StringBuilder(512);
        if (JavaCIPUnknownScope.debug.DEBUG)
            JavaCIPUnknownScope.debug.logger("gov.llnl.tox.util.href", "get(url)>> " + url);
        try {
            URL theURL = new URL(url);
            URLConnection urlConn = theURL.openConnection();
            urlConn.setDoOutput(true);
            urlConn.setReadTimeout(JavaCIPUnknownScope.timeOut);
            BufferedReader urlReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            do {
                buf = urlReader.readLine();
                if (buf != null) {
                    resultBuffer.append(buf);
                    resultBuffer.append("\n");
                }
            } while (buf != null);
            urlReader.close();
            if (JavaCIPUnknownScope.debug.DEBUG)
                JavaCIPUnknownScope.debug.logger("gov.llnl.tox.util.href", "get(output)>> " + resultBuffer.toString());
            int xmlNdx = resultBuffer.lastIndexOf("?>");
            if (xmlNdx == -1)
                JavaCIPUnknownScope.result = resultBuffer.toString();
            else
                JavaCIPUnknownScope.result = resultBuffer.substring(xmlNdx + 2);
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.result = JavaCIPUnknownScope.debug.logger("gov.llnl.tox.util.href", "error: get >> ", e);
        }
        return (JavaCIPUnknownScope.result);
    }
}
