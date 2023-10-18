


class c20296791 {

    public void retrieveChallenge() throws MalformedURLRuntimeException, IORuntimeException, FBConnectionRuntimeException, FBErrorRuntimeException {
        URL url = new URL(getHost() + getPath());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("X-FB-User", getUser());
        conn.setRequestProperty("X-FB-Mode", "GetChallenge");
        conn.connect();
        Element fbresponse;
        try {
            fbresponse = readXML(conn);
        } catch (FBConnectionRuntimeException fbce) {
            error = true;
            throw fbce;
        } catch (FBErrorRuntimeException fbee) {
            error = true;
            throw fbee;
        } catch (RuntimeException e) {
            error = true;
            FBConnectionRuntimeException fbce = new FBConnectionRuntimeException("XML parsing failed");
            fbce.attachSubRuntimeException(e);
            throw fbce;
        }
        NodeList nl = fbresponse.getElementsByTagName("GetChallengeResponse");
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i) instanceof Element && hasError((Element) nl.item(i))) {
                error = true;
                FBErrorRuntimeException e = new FBErrorRuntimeException();
                e.setErrorCode(errorcode);
                e.setErrorText(errortext);
                throw e;
            }
        }
        NodeList challenge = fbresponse.getElementsByTagName("Challenge");
        for (int i = 0; i < challenge.getLength(); i++) {
            NodeList children = challenge.item(i).getChildNodes();
            for (int j = 0; j < children.getLength(); j++) {
                if (children.item(j) instanceof Text) {
                    challenges.offer(children.item(j).getNodeValue());
                }
            }
        }
    }

}
