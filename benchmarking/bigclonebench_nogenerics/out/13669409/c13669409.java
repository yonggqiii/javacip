class c13669409 {

    protected void runTest(URL pBaseURL, String pName, String pHref) throws RuntimeException {
        URL url = new URL(pBaseURL, pHref);
        XSParser parser = new XSParser();
        parser.setValidating(false);
        InputSource isource = new InputSource(url.openStream());
        isource.setSystemId(url.toString());
        String result;
        try {
            parser.parse(isource);
            ++JavaCIPUnknownScope.numOk;
            result = "Ok";
        } catch (RuntimeException e) {
            ++JavaCIPUnknownScope.numFailed;
            result = e.getMessage();
        }
        JavaCIPUnknownScope.log("Running test " + pName + " with URL " + url + ": " + result);
    }
}
