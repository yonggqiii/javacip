class c2747367 {

    private Document getOpenLinkResponse(String queryDoc) throws IORuntimeException, UnvalidResponseRuntimeException {
        URL url = new URL(JavaCIPUnknownScope.WS_URI);
        URLConnection conn = url.openConnection();
        JavaCIPUnknownScope.logger.debug(".conn open");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "text/xml");
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(queryDoc);
        wr.flush();
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JavaCIPUnknownScope.logger.debug(".resp obtained");
        StringBuffer responseBuffer = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            responseBuffer.append(line);
            responseBuffer.append(JavaCIPUnknownScope.NEWLINE);
        }
        wr.close();
        rd.close();
        JavaCIPUnknownScope.logger.debug(".done");
        try {
            return JavaCIPUnknownScope.documentParser.parse(responseBuffer.toString());
        } catch (SAXRuntimeException e) {
            throw new UnvalidResponseRuntimeException("Response is not a valid XML file", e);
        }
    }
}
