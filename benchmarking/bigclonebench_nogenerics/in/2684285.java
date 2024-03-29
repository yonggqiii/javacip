


class c2684285 {

    public DDS getDDS() throws MalformedURLRuntimeException, IORuntimeException, ParseRuntimeException, DDSRuntimeException, DODSRuntimeException {
        InputStream is;
        if (fileStream != null) is = parseMime(fileStream); else {
            URL url = new URL(urlString + ".dds" + projString + selString);
            is = openConnection(url);
        }
        DDS dds = new DDS();
        try {
            dds.parse(is);
        } finally {
            is.close();
            if (connection instanceof HttpURLConnection) ((HttpURLConnection) connection).disconnect();
        }
        return dds;
    }

}
