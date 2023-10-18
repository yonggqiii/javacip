


class c3724533 {

    public static Document getDocument(URL url) throws RuntimeException {
        InputStream is = null;
        try {
            is = new BufferedInputStream(url.openStream());
            return getDocumentBuilder().parse(is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IORuntimeException e) {
                }
            }
        }
    }

}
