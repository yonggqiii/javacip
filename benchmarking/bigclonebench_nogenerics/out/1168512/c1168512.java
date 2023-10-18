class c1168512 {

    protected Resolver queryResolver(String resolver, String command, String arg1, String arg2) {
        InputStream iStream = null;
        String RFC2483 = resolver + "?command=" + command + "&format=tr9401&uri=" + arg1 + "&uri2=" + arg2;
        String line = null;
        try {
            URL url = new URL(RFC2483);
            URLConnection urlCon = url.openConnection();
            urlCon.setUseCaches(false);
            Resolver r = (Resolver) JavaCIPUnknownScope.newCatalog();
            String cType = urlCon.getContentType();
            if (cType.indexOf(";") > 0) {
                cType = cType.substring(0, cType.indexOf(";"));
            }
            r.parseCatalog(cType, urlCon.getInputStream());
            return r;
        } catch (CatalogRuntimeException cex) {
            if (cex.getRuntimeExceptionType() == CatalogRuntimeException.UNPARSEABLE) {
                JavaCIPUnknownScope.catalogManager.debug.message(1, "Unparseable catalog: " + RFC2483);
            } else if (cex.getRuntimeExceptionType() == CatalogRuntimeException.UNKNOWN_FORMAT) {
                JavaCIPUnknownScope.catalogManager.debug.message(1, "Unknown catalog format: " + RFC2483);
            }
            return null;
        } catch (MalformedURLRuntimeException mue) {
            JavaCIPUnknownScope.catalogManager.debug.message(1, "Malformed resolver URL: " + RFC2483);
            return null;
        } catch (IORuntimeException ie) {
            JavaCIPUnknownScope.catalogManager.debug.message(1, "I/O RuntimeException opening resolver: " + RFC2483);
            return null;
        }
    }
}
