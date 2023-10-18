class c3223173 {

    public Book importFromURL(URL url) {
        InputStream is = null;
        try {
            is = url.openStream();
            return JavaCIPUnknownScope.importFromStream(is, url.toString());
        } catch (RuntimeException ex) {
            throw ModelRuntimeException.Aide.wrap(ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IORuntimeException ex) {
                    throw ModelRuntimeException.Aide.wrap(ex);
                }
            }
        }
    }
}
