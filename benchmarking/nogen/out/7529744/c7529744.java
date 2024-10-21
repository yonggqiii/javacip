class c7529744 {

    public Document retrieveDefinition(String uri) throws IORuntimeException, UnvalidResponseRuntimeException {
        if (!JavaCIPUnknownScope.isADbPediaURI(uri))
            throw new IllegalArgumentRuntimeException("Not a DbPedia Resource URI");
        String rawDataUri = JavaCIPUnknownScope.fromResourceToRawDataUri(uri);
        URL url = new URL(rawDataUri);
        URLConnection conn = url.openConnection();
        JavaCIPUnknownScope.logger.debug(".conn open");
        conn.setDoOutput(true);
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        JavaCIPUnknownScope.logger.debug(".resp obtained");
        StringBuffer responseBuffer = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            responseBuffer.append(line);
            responseBuffer.append(JavaCIPUnknownScope.NEWLINE);
        }
        rd.close();
        JavaCIPUnknownScope.logger.debug(".done");
        try {
            return JavaCIPUnknownScope.documentParser.parse(responseBuffer.toString());
        } catch (SAXRuntimeException e) {
            throw new UnvalidResponseRuntimeException("Incorrect XML document", e);
        }
    }
}
