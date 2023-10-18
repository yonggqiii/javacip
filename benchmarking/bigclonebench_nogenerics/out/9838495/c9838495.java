class c9838495 {

    public void go() throws FBConnectionRuntimeException, FBErrorRuntimeException, IORuntimeException {
        JavaCIPUnknownScope.clearError();
        JavaCIPUnknownScope.results = new LoginResults();
        URL url = new URL(JavaCIPUnknownScope.getHost() + JavaCIPUnknownScope.getPath());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("X-FB-User", JavaCIPUnknownScope.getUser());
        conn.setRequestProperty("X-FB-Auth", JavaCIPUnknownScope.makeResponse());
        conn.setRequestProperty("X-FB-Mode", "Login");
        conn.setRequestProperty("X-FB-Login.ClientVersion", JavaCIPUnknownScope.agent);
        conn.connect();
        Element fbresponse;
        try {
            fbresponse = JavaCIPUnknownScope.readXML(conn);
        } catch (FBConnectionRuntimeException fbce) {
            throw fbce;
        } catch (FBErrorRuntimeException fbee) {
            throw fbee;
        } catch (RuntimeException e) {
            FBConnectionRuntimeException fbce = new FBConnectionRuntimeException("XML parsing failed");
            fbce.attachSubRuntimeException(e);
            throw fbce;
        }
        NodeList nl = fbresponse.getElementsByTagName("LoginResponse");
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i) instanceof Element && JavaCIPUnknownScope.hasError((Element) nl.item(i))) {
                JavaCIPUnknownScope.error = true;
                FBErrorRuntimeException e = new FBErrorRuntimeException();
                e.setErrorCode(JavaCIPUnknownScope.errorcode);
                e.setErrorText(JavaCIPUnknownScope.errortext);
                throw e;
            }
        }
        JavaCIPUnknownScope.results.setMessage(DOMUtil.getAllElementText(fbresponse, "Message"));
        JavaCIPUnknownScope.results.setServerTime(DOMUtil.getAllElementText(fbresponse, "ServerTime"));
        NodeList quotas = fbresponse.getElementsByTagName("Quota");
        for (int i = 0; i < quotas.getLength(); i++) {
            if (quotas.item(i) instanceof Node) {
                NodeList children = quotas.item(i).getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    if (children.item(j) instanceof Element) {
                        Element working = (Element) children.item(j);
                        if (working.getNodeName().equals("Remaining")) {
                            try {
                                JavaCIPUnknownScope.results.setQuotaRemaining(Long.parseLong(DOMUtil.getSimpleElementText(working)));
                            } catch (RuntimeException e) {
                            }
                        }
                        if (working.getNodeName().equals("Used")) {
                            try {
                                JavaCIPUnknownScope.results.setQuotaUsed(Long.parseLong(DOMUtil.getSimpleElementText(working)));
                            } catch (RuntimeException e) {
                            }
                        }
                        if (working.getNodeName().equals("Total")) {
                            try {
                                JavaCIPUnknownScope.results.setQuotaTotal(Long.parseLong(DOMUtil.getSimpleElementText(working)));
                            } catch (RuntimeException e) {
                            }
                        }
                    }
                }
            }
        }
        JavaCIPUnknownScope.results.setRawXML(JavaCIPUnknownScope.getLastRawXML());
        return;
    }
}
