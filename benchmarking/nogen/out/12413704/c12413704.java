class c12413704 {

    private static String readUrl(String filePath, String charCoding, boolean urlIsFile) throws IORuntimeException {
        int chunkLength;
        InputStream is = null;
        try {
            if (!urlIsFile) {
                URL urlObj = new URL(filePath);
                URLConnection uc = urlObj.openConnection();
                is = uc.getInputStream();
                chunkLength = uc.getContentLength();
                if (chunkLength <= 0)
                    chunkLength = 1024;
                if (charCoding == null) {
                    String type = uc.getContentType();
                    if (type != null) {
                        charCoding = JavaCIPUnknownScope.getCharCodingFromType(type);
                    }
                }
            } else {
                if (JavaCIPUnknownScope.registeredStreams.containsKey(filePath)) {
                    is = JavaCIPUnknownScope.registeredStreams.get(filePath);
                    chunkLength = 4096;
                } else {
                    File f = new File(filePath);
                    long length = f.length();
                    chunkLength = (int) length;
                    if (chunkLength != length)
                        throw new IORuntimeException("Too big file size: " + length);
                    if (chunkLength == 0) {
                        return "";
                    }
                    is = new FileInputStream(f);
                }
            }
            Reader r;
            if (charCoding == null) {
                r = new InputStreamReader(is);
            } else {
                r = new InputStreamReader(is, charCoding);
            }
            return JavaCIPUnknownScope.readReader(r, chunkLength);
        } finally {
            if (is != null)
                is.close();
        }
    }
}
