class c2684284 {

    public DAS getDAS() throws MalformedURLRuntimeException, IORuntimeException, ParseRuntimeException, DASRuntimeException, DODSRuntimeException {
        InputStream is;
        if (JavaCIPUnknownScope.fileStream != null)
            is = JavaCIPUnknownScope.parseMime(JavaCIPUnknownScope.fileStream);
        else {
            URL url = new URL(JavaCIPUnknownScope.urlString + ".das" + JavaCIPUnknownScope.projString + JavaCIPUnknownScope.selString);
            if (JavaCIPUnknownScope.dumpDAS) {
                System.out.println("--DConnect.getDAS to " + url);
                JavaCIPUnknownScope.copy(url.openStream(), System.out);
                System.out.println("\n--DConnect.getDAS END1");
                JavaCIPUnknownScope.dumpBytes(url.openStream(), 100);
                System.out.println("\n-DConnect.getDAS END2");
            }
            is = JavaCIPUnknownScope.openConnection(url);
        }
        DAS das = new DAS();
        try {
            das.parse(is);
        } finally {
            is.close();
            if (JavaCIPUnknownScope.connection instanceof HttpURLConnection)
                ((HttpURLConnection) JavaCIPUnknownScope.connection).disconnect();
        }
        return das;
    }
}
