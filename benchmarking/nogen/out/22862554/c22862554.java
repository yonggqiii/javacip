class c22862554 {

    public void call(String soapAction, SoapEnvelope envelope) throws IORuntimeException, XmlPullParserRuntimeException {
        if (soapAction == null) {
            soapAction = "\"\"";
        }
        byte[] requestData = JavaCIPUnknownScope.createRequestData(envelope);
        JavaCIPUnknownScope.requestDump = JavaCIPUnknownScope.debug ? new String(requestData) : null;
        JavaCIPUnknownScope.responseDump = null;
        HttpPost method = new HttpPost(JavaCIPUnknownScope.url);
        method.addHeader("User-Agent", "kSOAP/2.0-Excilys");
        method.addHeader("SOAPAction", soapAction);
        method.addHeader("Content-Type", "text/xml");
        HttpEntity entity = new ByteArrayEntity(requestData);
        method.setEntity(entity);
        HttpResponse response = JavaCIPUnknownScope.httpClient.execute(method);
        InputStream inputStream = response.getEntity().getContent();
        if (JavaCIPUnknownScope.debug) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[256];
            while (true) {
                int rd = inputStream.read(buf, 0, 256);
                if (rd == -1) {
                    break;
                }
                bos.write(buf, 0, rd);
            }
            bos.flush();
            buf = bos.toByteArray();
            JavaCIPUnknownScope.responseDump = new String(buf);
            inputStream.close();
            inputStream = new ByteArrayInputStream(buf);
        }
        JavaCIPUnknownScope.parseResponse(envelope, inputStream);
        inputStream.close();
    }
}
