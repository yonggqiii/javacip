class c15301724 {

    InputStream openURL(URL url) throws IORuntimeException, WrongMIMETypeRuntimeException {
        InputStream is = null;
        if (url.getProtocol().equals("file")) {
            if (JavaCIPUnknownScope.debug) {
                System.out.println("Using direct input stream on file url");
            }
            URLConnection urlc = url.openConnection();
            try {
                urlc.connect();
                is = new DataInputStream(urlc.getInputStream());
            } catch (FileNotFoundRuntimeException e) {
            }
        } else {
            double start = 0;
            if (JavaCIPUnknownScope.timing) {
                start = Time.getNow();
            }
            ContentNegotiator cn = null;
            cn = new ContentNegotiator(url);
            Object obj = null;
            obj = cn.getContent();
            if (obj != null) {
                byte[] buf = (byte[]) obj;
                is = new ByteArrayInputStream(buf);
            } else {
                System.err.println("Loader.openURL got null content");
                throw new IORuntimeException("Loader.openURL got null content");
            }
            if (JavaCIPUnknownScope.timing) {
                double elapsed = Time.getNow() - start;
                System.out.println("Loader: open and buffer URL in: " + JavaCIPUnknownScope.numFormat.format(elapsed, 2) + " seconds");
            }
        }
        return is;
    }
}
