class c20296792 {

    public void retrieveChallenges(int num) throws MalformedURLRuntimeException, IORuntimeException, FBErrorRuntimeException, FBConnectionRuntimeException {
        if (num < 1 || num > 100) {
            JavaCIPUnknownScope.error = true;
            FBErrorRuntimeException fbee = new FBErrorRuntimeException();
            fbee.setErrorCode(-100);
            fbee.setErrorText("Invalid GetChallenges range");
            throw fbee;
        }
        URL url = new URL(JavaCIPUnknownScope.getHost() + JavaCIPUnknownScope.getPath());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("X-FB-User", JavaCIPUnknownScope.getUser());
        conn.setRequestProperty("X-FB-Mode", "GetChallenges");
        conn.setRequestProperty("X-FB-GetChallenges.Qty", new Integer(num).toString());
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
        NodeList nl = fbresponse.getElementsByTagName("GetChallengesResponse");
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
