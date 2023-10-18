class c2684285 {

    public DDS getDDS() throws MalformedURLRuntimeException, IORuntimeException, ParseRuntimeException, DDSRuntimeException, DODSRuntimeException {
        InputStream is;
        if (JavaCIPUnknownScope.fileStream != null)
            is = JavaCIPUnknownScope.parseMime(JavaCIPUnknownScope.fileStream);
        else {
            URL url = new URL(JavaCIPUnknownScope.urlString + ".dds" + JavaCIPUnknownScope.projString + JavaCIPUnknownScope.selString);
            is = JavaCIPUnknownScope.openConnection(url);
        }
        DDS dds = new DDS();
        try {
            dds.parse(is);
        } finally {
            is.close();
            if (JavaCIPUnknownScope.connection instanceof HttpURLConnection)
                ((HttpURLConnection) JavaCIPUnknownScope.connection).disconnect();
        }
        return dds;
    }
}
