


class c8706253 {

    private boolean parse(Type type, URL url, boolean checkDict) throws RuntimeException {
        boolean ok = true;
        RuntimeException ee = null;
        Element rootElement = null;
        try {
            InputStream in = url.openStream();
            if (type.equals(Type.XOM)) {
                new Builder().build(in);
            } else if (type.equals(Type.CML)) {
                rootElement = new CMLBuilder().build(in).getRootElement();
            }
            in.close();
        } catch (RuntimeException e) {
            ee = e;
        }
        if (ee != null) {
            logger.severe("failed to cmlParse: " + url + "\n..... because: [" + ee + "] [" + ee.getMessage() + "] in [" + url + "]");
            ok = false;
        }
        if (ok && checkDict) {
            ok = checkDict(rootElement);
        }
        return ok;
    }

}
