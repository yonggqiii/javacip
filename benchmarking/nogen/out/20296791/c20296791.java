class c20296791 {

    public void retrieveChallenge() throws MalformedURLRuntimeException, IORuntimeException, FBConnectionRuntimeException, FBErrorRuntimeException {
        URL url = new URL(JavaCIPUnknownScope.getHost() + JavaCIPUnknownScope.getPath());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("X-FB-User", JavaCIPUnknownScope.getUser());
        conn.setRequestProperty("X-FB-Mode", "GetChallenge");
        conn.connect();
        Element fbresponse;
        try {
            fbresponse = JavaCIPUnknownScope.readXML(conn);
        } catch (FBConnectionRuntimeException fbce) {
            JavaCIPUnknownScope.error = true;
            throw fbce;
        } catch (FBErrorRuntimeException fbee) {
            JavaCIPUnknownScope.error = true;
            throw fbee;
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.error = true;
            FBConnectionRuntimeException fbce = new FBConnectionRuntimeException("XML parsing failed");
            fbce.attachSubRuntimeException(e);
            throw fbce;
        }
        NodeList nl = fbresponse.getElementsByTagName("GetChallengeResponse");
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i) instanceof Element && JavaCIPUnknownScope.hasError((Element) nl.item(i))) {
                JavaCIPUnknownScope.error = true;
                FBErrorRuntimeException e = new FBErrorRuntimeException();
                e.setErrorCode(JavaCIPUnknownScope.errorcode);
                e.setErrorText(JavaCIPUnknownScope.errortext);
                throw e;
            }
        }
        NodeList challenge = fbresponse.getElementsByTagName("Challenge");
        for (int i = 0; i < challenge.getLength(); i++) {
            NodeList children = challenge.item(i).getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                if (children.item(j) instanceof Text) {
                    JavaCIPUnknownScope.challenges.offer(children.item(j).getNodeValue());
                }
            }
        }
    }
}
